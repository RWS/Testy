using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestyForC.Web;

namespace Testy.Web
{
    public class SearchText
    {
        private String value;
        private List<SearchType> searchTypes = new List<SearchType>();

        public SearchText(String value, List<SearchType> searchTypes)
        {
            this.Value = value;
            this.SearchTypes = searchTypes;
        }

        public string Value { get => value; set => this.value = value; }
        public List<SearchType> SearchTypes { get => searchTypes; set => searchTypes = value; }
    }
}
