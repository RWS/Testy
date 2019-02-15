using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Testy.Web;

namespace TestyForC.Web
{
    public class XPathBuilder
    {
        private String root = "//";
        private String tag = "*";
        private String id;
        private String elPath;
        private String elCssSelector;
        private String baseCls;
        private String cls;
        private List<String> classes;
        private List<String> excludeClasses;
        private String name;
        private String text;
        private List<SearchType> searchTextType = new List<SearchType> { new SearchType().Contains()};
        private List<SearchType> searchTitleType = new List<SearchType>();
        private List<SearchType> searchLabelType = new List<SearchType>();
        private String style;
        private String title;
        private Dictionary<String, String> templates = new Dictionary<String, String>();
        private Dictionary<String, WebLocator> templateTitle = new Dictionary<String, WebLocator>();
        private Dictionary<String, String[]> templatesValues = new Dictionary<String, String[]>();
        private Dictionary<String, String> elPathSuffix = new Dictionary<String, String>();

        private String infoMessage;

        private String label;
        private String labelTag = "label";
        private String labelPosition = "//following-sibling::*//";

        private int position;
        private int resultIdx;
        private String type;
        private Dictionary<String, SearchText> attribute = new Dictionary<String, SearchText>();
       
        private bool visibility;
        private long renderMillis = 5;
        private int activateSeconds = 60;

        private WebLocator container;
        private List<WebLocator> childNodes;

        public XPathBuilder() {
            Templates = new Dictionary<string, string>
            {
                { "visibility", "count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0" },
                { "style", "contains(@style, '{0}')" },
                { "id", "@id='{0}'" },
                { "name", "@name='{0}'" },
                { "class", "contains(concat(' ', @class, ' '), ' {0} ')" },
                { "excludeClass", "not(contains(@class, '{0}'))" },
                { "cls", "@class='{0}'" },
                { "type", "@type='{0}'" },
                { "title", "@title='{0}'" },
                { "titleEl", "count(.{0}) > 0" },
                { "DEEP_CHILD_NODE_OR_SELF", "({0} or count(*//text()[{0}]) > 0)" },
                { "DEEP_CHILD_NODE", "count(*//text()[{0}]) > 0" },
                { "CHILD_NODE", "count(text()[{0}]) > 0" },
                { "HTML_NODE", "(normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))={0} or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))={0})" },
                { "childNodes", "count(.{0}) > 0" }
            };
        }

        public string Root { get => root; set => root = value; }
        public string Tag { get => tag; set => tag = value; }
        public string Id { get => id; set => id = value; }
        public string ElPath { get => elPath; set => elPath = value; }
        public string ElCssSelector { get => elCssSelector; set => elCssSelector = value; }
        public string BaseCls { get => baseCls; set => baseCls = value; }
        public string Cls { get => cls; set => cls = value; }
        public List<string> Classes { get => classes; set => classes = value; }
        public List<string> ExcludeClasses { get => excludeClasses; set => excludeClasses = value; }
        public string Name { get => name; set => name = value; }
        public string Text { get => text; set => text = value; }
        public List<SearchType> SearchTextType { get => searchTextType; set => searchTextType = value; }
        public List<SearchType> SearchTitleType { get => searchTitleType; set => searchTitleType = value; }
        public List<SearchType> SearchLabelType { get => searchLabelType; set => searchLabelType = value; }
        public string Style { get => style; set => style = value; }
        public string Title { get => title; set => title = value; }
        public Dictionary<string, string> Templates { get => templates; set => templates = value; }
        public Dictionary<string, WebLocator> TemplateTitle { get => templateTitle; set => templateTitle = value; }
        public Dictionary<string, string[]> TemplatesValues { get => templatesValues; set => templatesValues = value; }
        public Dictionary<string, string> ElPathSuffix { get => elPathSuffix; set => elPathSuffix = value; }
        public string InfoMessage { get => infoMessage; set => infoMessage = value; }
        public string Label { get => label; set => label = value; }
        public string LabelTag { get => labelTag; set => labelTag = value; }
        public string LabelPosition { get => labelPosition; set => labelPosition = value; }
        public int Position { get => position; set => position = value; }
        public int ResultIdx { get => resultIdx; set => resultIdx = value; }
        public string Type { get => type; set => type = value; }
        public Dictionary<string, SearchText> Attribute { get => attribute; set => attribute = value; }
        public bool Visibility { get => visibility; set => visibility = value; }
        public long RenderMillis { get => renderMillis; set => renderMillis = value; }
        public int ActivateSeconds { get => activateSeconds; set => activateSeconds = value; }
        public WebLocator Container { get => container; set => container = value; }
        public List<WebLocator> ChildNodes { get => childNodes; set => childNodes = value; }

        public void setTemplate(String key, String value)
        {
            if (value == null)
            {
                templates.Remove(key);
            }
            else
            {
                templates.Add(key, value);
            }
        }

        protected bool hasId()
        {
            return Id != null && !"".Equals(Id);
        }

        protected bool hasCls()
        {
            return Cls != null && !"".Equals(Cls);
        }

        protected bool hasClasses()
        {
            return Classes != null && Classes.Count() > 0;
        }

        protected bool hasChildNodes()
        {
            return childNodes != null && childNodes.Count() > 0;
        }

        protected bool hasExcludeClasses()
        {
            return excludeClasses != null && excludeClasses.Count() > 0;
        }

        protected bool hasBaseCls()
        {
            return BaseCls != null && !"".Equals(BaseCls);
        }

        protected bool hasName()
        {
            return Name != null && !"".Equals(Name);
        }

        protected bool hasText()
        {
            return Text != null && !"".Equals(Text);
        }

        protected bool hasStyle()
        {
            return Style != null && !"".Equals(Style);
        }

        protected bool hasElPath()
        {
            return ElPath != null && !"".Equals(ElPath);
        }

        protected bool hasTag()
        {
            return Tag != null && !"*".Equals(Tag);
        }

        protected bool hasLabel()
        {
            return Label != null && !"".Equals(Label);
        }

        protected bool hasTitle()
        {
            return Title != null && !"".Equals(Title) || templateTitle.Count() != 0;
        }

        protected bool hasPosition()
        {
            return Position > 0;
        }

        protected bool hasResultIdx()
        {
            return ResultIdx > 0;
        }

        protected bool hasType()
        {
            return Type != null && !"".Equals(Type);
        }

        public string XPath()
        {
            String returnPath;
            if (hasElPath())
            {
                returnPath = ElPath;
            }
            else
            {
                returnPath = getItemPath();
            }

            returnPath = afterItemPathCreated(returnPath);

            // add container path
            if (Container != null)
            {
                returnPath = Container.XPath() + returnPath;
            }
            return addResultIndexToPath(returnPath);
        }

        private String addResultIndexToPath(String xPath)
        {
            if (hasResultIdx())
            {
                xPath = "(" + xPath + ")[" + ResultIdx + "]";
            }
            return xPath;
        }

        protected String afterItemPathCreated(String itemPath)
        {
            if (hasLabel())
            {
                // remove '//' because labelPath already has and include
                if (itemPath.IndexOf("//") == 0)
                {
                    itemPath = itemPath.Substring(2);
                }
                itemPath = getLabelPath() + LabelPosition + itemPath;
            }
            itemPath = addPositionToPath(itemPath);
            return itemPath;
        }

        protected String getLabelPath()
        {
            if (searchLabelType.Count == 0)
            {
                searchLabelType.Add(new SearchType().Equals());
            }
            return new WebLocator().setText(Label, searchLabelType).setTag(LabelTag).XPath();
        }

        protected String addPositionToPath(String itemPath)
        {
            if (hasPosition())
            {
                itemPath += "[position() = " + Position + "]";
            }
            return itemPath;
        }

        protected String getItemPath()
        {
            String selector = getBasePathSelector();
            //String subPath = applyTemplateValue(disabled ? "disabled" : "enabled");
            //if (subPath != null)
            //{
            //    selector += !Strings.isNullOrEmpty(selector) ? " and " + subPath : subPath;
            //}
            selector = Root + Tag + (selector != null && !"".Equals(selector) ? "[" + selector + "]" : "");
            return selector;
        }

        protected String getBasePathSelector()
        {
            List<String> selector = new List<String>();
            selector.Add(getBasePath());
            if (hasStyle())
            {
                selector.Add(applyTemplate("style", Style));
            }
            if (Visibility)
            {
                selector.Add(applyTemplate("visibility", Visibility));
            }
            return selector.Count == 0 ? "" : String.Join(" and ", selector);
        }

        protected String applyTemplate(String key, Object arguments)
        {
            String tpl = templates[key];
            if (tpl != null && !"".Equals(tpl))
            {
                return String.Format(tpl, arguments);
            }
            return null;
        }

        private void addTemplate(List<String> selector, String key, Object arguments)
        {
            String tpl = applyTemplate(key, arguments);
            if (tpl != null && !"".Equals(tpl))
            {
                selector.Add(tpl);
            }
        }

        public String getBasePath()
        {
            List<String> selector = new List<String>();
            if (hasId())
            {
                selector.Add(applyTemplate("id", Id));
            }
            if (hasName())
            {
                selector.Add(applyTemplate("name", Name));
            }
            if (hasBaseCls())
            {
                selector.Add(applyTemplate("class", BaseCls));
            }
            if (hasCls())
            {
                selector.Add(applyTemplate("cls", Cls));
            }
            if (hasClasses())
            {
                foreach (string cls in Classes)
                {
                    selector.Add(applyTemplate("class", cls));
                }
            }
            if (hasExcludeClasses())
            {
                foreach (string cls in ExcludeClasses)
                {
                    selector.Add(applyTemplate("excludeClass", cls));
                }
            }
            if (hasTitle())
            {
                String title = Title;
                WebLocator titleTplEl;
                TemplateTitle.TryGetValue("title", out titleTplEl);
                //WebLocator titleTplEl = templateTitle["title"];
                if (title != null && !"".Equals(title) || titleTplEl != null)
                {
                    if (titleTplEl != null)
                    {
                        if (title != null && !"".Equals(title))
                        {
                            titleTplEl.setText(title, SearchTitleType);
                        }
                        if (titleTplEl.getXPathBuilder().Text != null)
                        {
                            addTemplate(selector, "titleEl", titleTplEl.XPath());
                        }
                    }
                    else if (searchTitleType.Count() == 0)
                    {
                        addTemplate(selector, "title", title);
                    }
                    else
                    {
                        addTextInPath(selector, title, "@title", searchTitleType);
                    }
                }
            }
            if (hasType())
            {
                addTemplate(selector, "type", Type);
            }
            if (attribute.Count != 0 )
            {
                foreach (KeyValuePair<string, SearchText> entry in Attribute)
                {
                    List<SearchType> searchType = entry.Value.SearchTypes;
                    String text = entry.Value.Value;
                    addTextInPath(selector, text, "@" + entry.Key, searchType);
                }
            }
            if (hasText())
            {
                addTextInPath(selector, Text, ".", searchTextType);
            }
           
            foreach (KeyValuePair<string, string[]> entry in TemplatesValues)
            {
                addTemplate(selector, entry.Key, entry.Value);
            }

            foreach (KeyValuePair<string, string> entry in ElPathSuffix)
            {
                selector.Add(entry.Value);
            }
            selector.AddRange(getChildNodesToSelector());
            return selector.Count == 0 ? null : String.Join(" and ", selector);
        }

        private List<String> getChildNodesToSelector()
        {
            List<String> selector = new List<String>();
            if (hasChildNodes())
            {
                foreach (WebLocator child in ChildNodes)
                {
                    selector.Add(getChildNodeSelector(child));
                }
            }
            return selector;
        }

        private String getChildNodeSelector(WebLocator child)
        {
            WebLocator breakElement = null;
            WebLocator childIterator = child;
            WebLocator parentElement = null;
            // break parent tree if is necessary
            while (childIterator.getXPathBuilder().Container != null && breakElement == null)
            {
                WebLocator parentElementIterator = childIterator.getXPathBuilder().Container;

                // child element has myself as parent
                if (parentElementIterator.getXPathBuilder() == this)
                {
                    childIterator.setContainer(null); // break parent tree while generating child address
                    parentElement = parentElementIterator;
                    breakElement = childIterator;
                }
                else
                {
                    childIterator = parentElementIterator;
                }
            }

            String selector = applyTemplate("childNodes", child.XPath());
            if (breakElement != null)
            {
                breakElement.setContainer(parentElement);
            }
            return selector;
        }

        public void addTextInPath(List<String> selector, String text, String pattern, List<SearchType> searchTextType)
        {
            text = getTextAfterEscapeQuotes(text, searchTextType);
            bool hasContainsAll = searchTextType.Contains(new SearchType().ContainsAll()) || searchTextType.Contains(new SearchType().ContainsAllChildNodes());
            string tpl;
            Templates.TryGetValue("text", out tpl);
            if (tpl != null && !"".Equals(tpl))
            {
                selector.Add(String.Format(tpl, text));
            }
            else if (hasContainsAll || searchTextType.Contains(new SearchType().ContainsAny()))
            {
                char splitChar = text[0];
                String[] strings = text.Split(splitChar);
                for (int i = 0; i < strings.Length; i++)
                {
                    String escapeQuotesText = getEscapeQuotesText(strings[i]);
                    if (searchTextType.Contains(new SearchType().ContainsAllChildNodes()))
                    {
                        if (searchTextType.Contains(new SearchType().CaseInsensitive()))
                        {
                            strings[i] = "count(*//text()[contains(translate(.," + escapeQuotesText.ToUpper().Replace("CONCAT\\(", "concat(") + "," + escapeQuotesText.ToLower() + ")," + escapeQuotesText.ToLower() + ")]) > 0";
                        }
                        else
                        {
                            strings[i] = "count(*//text()[contains(.," + escapeQuotesText + ")]) > 0";
                        }
                    }
                    else
                    {
                        if (searchTextType.Contains(new SearchType().DeepChildNodeOrSelf()))
                        {
                            strings[i] = applyTemplate("DEEP_CHILD_NODE_OR_SELF", escapeQuotesText);
                        }
                        else if (searchTextType.Contains(new SearchType().DeepChildNode()))
                        {
                            strings[i] = applyTemplate("DEEP_CHILD_NODE", escapeQuotesText);
                        }
                        else
                        {
                            strings[i] = "contains(" + (".".Equals(pattern) ? "." : pattern) + "," + escapeQuotesText + ")";
                        }
                    }
                }
                selector.Add(hasContainsAll ? String.Join(" and ", strings) : "(" + String.Join(" or ", strings) + ")");
            }
            else if (searchTextType.Contains(new SearchType().DeepChildNodeOrSelf()))
            {
                String selfPath = getTextWithSearchType(searchTextType, text, pattern);
                addTemplate(selector, "DEEP_CHILD_NODE_OR_SELF", selfPath);
            }
            else if (searchTextType.Contains(new SearchType().DeepChildNode()))
            {
                String selfPath = getTextWithSearchType(searchTextType, text, pattern);
                addTemplate(selector, "DEEP_CHILD_NODE", selfPath);
            }
            else if (searchTextType.Contains(new SearchType().ChildNode()))
            {
                String selfPath = getTextWithSearchType(searchTextType, text, pattern);
                addTemplate(selector, "CHILD_NODE", selfPath);
            }
            else if (searchTextType.Contains(new SearchType().HtmlNode()))
            {
                addTemplate(selector, "HTML_NODE", text);
            }
            else
            {
                selector.Add(getTextWithSearchType(searchTextType, text, ".".Equals(pattern) ? "text()" : pattern));
            }
        }

        public static String getEscapeQuotesText(String text)
        {
            bool hasDoubleQuote = text.Contains("\"");
            bool hasSingeQuote = text.Contains("'");
            if (hasDoubleQuote && hasSingeQuote)
            {
                bool quoteIsLast = false;
                if (text.LastIndexOf("\"") == text.Length - 1)
                {
                    quoteIsLast = true;
                }
                String[] substrings = text.Split(new Char[] { '\"' });

                StringBuilder quoted = new StringBuilder("concat(");
                for (int i = 0; i < substrings.Length; i++)
                {
                    quoted.Append("\"").Append(substrings[i]).Append("\"");
                    quoted.Append(((i == substrings.Length - 1) ? (quoteIsLast ? ", '\"')" : ")") : ", '\"', "));
                }
                return quoted.ToString();
            }
            else if (hasDoubleQuote || !hasSingeQuote)
            {
                return String.Format("'{0}'", text);
            }
            return String.Format("\"{0}\"", text);
        }

        private String getTextAfterEscapeQuotes(String text, List<SearchType> searchType)
        {
            if (searchType.Contains(new SearchType().ContainsAll()) || searchType.Contains(new SearchType().ContainsAny()) || searchType.Contains(new SearchType().ContainsAllChildNodes()))
            {
                return text;
            }
            return getEscapeQuotesText(text);
        }

        private String getTextWithSearchType(List<SearchType> searchType, String text, String pattern)
        {
            if (searchType.Contains(new SearchType().Trim()))
            {
                pattern = "normalize-space(" + pattern + ")";
            }
            if (searchType.Contains(new SearchType().CaseInsensitive()))
            {
                pattern = "translate(" + pattern + "," + text.ToUpper().Replace("CONCAT\\(", "concat(") + "," + text.ToLower() + ")";
                text = text.ToLower();
            }
            if (searchType.Contains(new SearchType().Equals()))
            {
                text = pattern + "=" + text;
            }
            else if (searchType.Contains(new SearchType().StartWith()))
            {
                text = "starts-with(" + pattern + "," + text + ")";
            }
            else
            {
                text = "contains(" + pattern + "," + text + ")";
            }
            return text;
        }
    }
}
