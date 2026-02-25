package com.sdl.unit.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sdl.selenium.web.Transform;
import lombok.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TransformTest implements Transform {

    // -----------------------------------------------------------------------
    // Simple DTO used as "type" template for transformTo()
    // -----------------------------------------------------------------------
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PersonTO {
        private String firstName;
        private String lastName;
        private String email;
    }

    // -----------------------------------------------------------------------
    // Helper: create a blank template instance (all fields set to "" so that
    // getNames() picks them up via textValue() != null check)
    // -----------------------------------------------------------------------
    private PersonTO template() {
        PersonTO t = new PersonTO();
        t.setFirstName("");
        t.setLastName("");
        t.setEmail("");
        return t;
    }

    // -----------------------------------------------------------------------
    // DataProvider
    // -----------------------------------------------------------------------
    @DataProvider
    public static Object[][] transformToDataProvider() {
        return new Object[][]{
                // --- 1. Normal case: one row, all columns present ---
                {
                        List.of(
                                List.of("John", "Doe", "john@example.com")
                        ),
                        List.of(),                 // no columns excluded
                        List.of(new PersonTO() {{
                            setFirstName("John");
                            setLastName("Doe");
                            setEmail("john@example.com");
                        }})
                },
                // --- 2. Multiple rows ---
                {
                        List.of(
                                List.of("Alice", "Smith", "alice@example.com"),
                                List.of("Bob", "Brown", "bob@example.com")
                        ),
                        List.of(),
                        List.of(
                                new PersonTO() {{
                                    setFirstName("Alice");
                                    setLastName("Smith");
                                    setEmail("alice@example.com");
                                }},
                                new PersonTO() {{
                                    setFirstName("Bob");
                                    setLastName("Brown");
                                    setEmail("bob@example.com");
                                }}
                        )
                },
                // --- 3. Fewer data columns than DTO fields -> last field becomes null ---
                {
                        List.of(
                                List.of("Jane", "Doe")   // email missing
                        ),
                        List.of(),
                        List.of(new PersonTO() {{
                            setFirstName("Jane");
                            setLastName("Doe");
                            // email stays null
                        }})
                },
                // --- 4. Exclude first column (firstName, index 1): data has 2 values -> lastName + email ---
                {
                        List.of(
                                List.of("Doe", "jane@example.com")   // firstName excluded from data
                        ),
                        List.of(1),   // exclude index 1 = firstName
                        List.of(new PersonTO() {{
                            setFirstName("");  // excluded field keeps template value ""
                            setLastName("Doe");
                            setEmail("jane@example.com");
                        }})
                },
                // --- 5. Exclude last column (email, index 3): data has 2 values -> firstName + lastName ---
                {
                        List.of(
                                List.of("John", "Doe")   // email excluded from data
                        ),
                        List.of(3),   // exclude index 3 = email
                        List.of(new PersonTO() {{
                            setFirstName("John");
                            setLastName("Doe");
                            setEmail("");  // excluded field keeps template value ""
                        }})
                },
                // --- 6. Exclude middle column (lastName, index 2): data has 2 values -> firstName + email ---
                {
                        List.of(
                                List.of("Alice", "alice@example.com")   // lastName excluded from data
                        ),
                        List.of(2),   // exclude index 2 = lastName
                        List.of(new PersonTO() {{
                            setFirstName("Alice");
                            setLastName("");  // excluded field keeps template value ""
                            setEmail("alice@example.com");
                        }})
                },
                // --- 7. Exclude multiple columns (firstName + email, indices 1 and 3): data has 1 value -> only lastName ---
                {
                        List.of(
                                List.of("Smith")   // only lastName in data
                        ),
                        List.of(1, 3),   // exclude index 1 = firstName, index 3 = email
                        List.of(new PersonTO() {{
                            setFirstName("");  // excluded field keeps template value ""
                            setLastName("Smith");
                            setEmail("");  // excluded field keeps template value ""
                        }})
                },
                // --- 8. Exclude column with multiple rows ---
                {
                        List.of(
                                List.of("Alice", "alice@example.com"),
                                List.of("Bob", "bob@example.com")
                        ),
                        List.of(2),   // exclude index 2 = lastName
                        List.of(
                                new PersonTO() {{
                                    setFirstName("Alice");
                                    setLastName("");  // excluded field keeps template value ""
                                    setEmail("alice@example.com");
                                }},
                                new PersonTO() {{
                                    setFirstName("Bob");
                                    setLastName("");  // excluded field keeps template value ""
                                    setEmail("bob@example.com");
                                }}
                        )
                },
                // --- 9. type == null -> raw list is returned unchanged ---
                // (tested separately below, because the return type is List<List<String>>)
        };
    }

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    @Test(dataProvider = "transformToDataProvider")
    public void transformTo_shouldMapRowsToObjects(
            List<List<String>> rows,
            List<Integer> excludedColumns,
            List<PersonTO> expected) {

        List<PersonTO> result = transformTo(template(), rows, excludedColumns);

        assertThat(result, hasSize(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat("row " + i, result.get(i), equalTo(expected.get(i)));
        }
    }

    @Test
    public void transformTo_whenTypeIsNull_shouldReturnRawList() {
        List<List<String>> rows = List.of(
                List.of("A", "B"),
                List.of("C", "D")
        );

        // When type is null the method casts and returns the raw list as-is
        List<List<String>> result = transformTo(null, rows, List.of());

        assertThat(result, equalTo(rows));
    }

    @Test
    public void transformTo_withExcludedColumns_shouldSkipThoseFields() {
        // DTO has 3 fields: firstName(1), lastName(2), email(3)
        // When names.size() > row.size() the method removes names at the given 1-based indices.
        // The excluded field is not overwritten, so it keeps the template value "".
        List<List<String>> rows = List.of(
                List.of("John", "john@example.com")  // lastName skipped in data
        );

        List<PersonTO> result = transformTo(template(), rows, List.of(2));

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getFirstName(), equalTo("John"));
        assertThat(result.get(0).getEmail(), equalTo("john@example.com"));
        assertThat(result.get(0).getLastName(), equalTo(""));  // excluded -> keeps template value ""
    }

    @Test
    public void transformTo_withSingleRow_shouldReturnListWithOneElement() {
        List<List<String>> rows = List.of(
                List.of("Solo", "User", "solo@example.com")
        );

        List<PersonTO> result = transformTo(template(), rows, List.of());

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getFirstName(), equalTo("Solo"));
        assertThat(result.get(0).getLastName(), equalTo("User"));
        assertThat(result.get(0).getEmail(), equalTo("solo@example.com"));
    }
}

