using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestyForC.Web
{
    public class SearchType
    {
        private String group;
        private String value;

        public SearchType()
        {
        }

        public SearchType(string group, string value)
        {
            this.group = group;
            this.value = value;
        }

        public SearchType Equals()
        {
            return new SearchType("text", "equals");
        }

        public SearchType Contains()
        {
            return new SearchType("text", "contains");
        }

        public SearchType StartWith()
        {
            return new SearchType("text", "startWith");
        }
        public SearchType Trim()
        {
            return new SearchType("trim", "trim");
        }
        public SearchType NoTrim()
        {
            return new SearchType("trim", "noTrim");
        }
        public SearchType CaseInsensitive()
        {
            return new SearchType("sensitive", "caseInsensitive");
        }
        public SearchType CaseSensitive()
        {
            return new SearchType("sensitive", "caseSensitive");
        }
        public SearchType ChildNode()
        {
            return new SearchType("child", "childNode");
        }
        public SearchType DeepChildNode()
        {
            return new SearchType("child", "deepChildNode");
        }
        public SearchType DeepChildNodeOrSelf()
        {
            return new SearchType("child", "deepChildNodeOrSelf");
        }
        public SearchType HtmlNode()
        {
            return new SearchType("advance", "htmlNode");
        }
        public SearchType ContainsAll()
        {
            return new SearchType("advance", "containsAll");
        }
        public SearchType ContainsAllChildNodes()
        {
            return new SearchType("advance", "containsAllChildNodes");
        }
        public SearchType ContainsAny()
        {
            return new SearchType("advance", "containsAny");
        }

        public override bool Equals(object obj)
        {
            var type = obj as SearchType;
            return type != null &&
                   group == type.group &&
                   value == type.value;
        }

        public override int GetHashCode()
        {
            var hashCode = 1856320566;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(group);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(value);
            return hashCode;
        }
    }
}