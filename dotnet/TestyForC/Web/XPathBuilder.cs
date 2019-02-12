using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        private String position;
        private String resultIdx;
        private String type;
        private Dictionary<String, SearchType> attribute = new Dictionary<String, SearchType>();
       
        private bool visibility;
        private long renderMillis = 5;
        private int activateSeconds = 60;

        private WebLocator container;
        private List<WebLocator> childNodes;

        public XPathBuilder() {
            templates = new Dictionary<string, string>
            {
                { "visibility", "count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0" },
                {"id", "@id='%s'" },
                { "name", "@name='%s'" },
                { "class", "contains(concat(' ', @class, ' '), ' %s ')" },
                { "excludeClass", "not(contains(@class, '%s'))" },
                { "cls", "@class='%s'" },
                { "type", "@type='%s'" },
                { "title", "@title='%s'" },
                { "titleEl", "count(.%s) > 0" },
                { "DEEP_CHILD_NODE_OR_SELF", "(%1$s or count(*//text()[%1$s]) > 0)" },
                { "DEEP_CHILD_NODE", "count(*//text()[%s]) > 0" },
                { "CHILD_NODE", "count(text()[%s]) > 0" },
                { "HTML_NODE", "(normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))=%1$s or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))=%1$s)" },
                { "childNodes", "count(.%s) > 0" }
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
       
        public Dictionary<string, WebLocator> TemplateTitle { get => templateTitle; set => templateTitle = value; }
        public Dictionary<string, string[]> TemplatesValues { get => templatesValues; set => templatesValues = value; }
        public Dictionary<string, string> ElPathSuffix { get => elPathSuffix; set => elPathSuffix = value; }
        public string InfoMessage { get => infoMessage; set => infoMessage = value; }
        public string Label { get => label; set => label = value; }
        public string LabelTag { get => labelTag; set => labelTag = value; }
        public string LabelPosition { get => labelPosition; set => labelPosition = value; }
        public string Position { get => position; set => position = value; }
        public string ResultIdx { get => resultIdx; set => resultIdx = value; }
        public string Type { get => type; set => type = value; }
        public Dictionary<string, SearchType> Attribute { get => attribute; set => attribute = value; }
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
        public string XPath()
        {
            return Root + Tag;
        }
    }
}
