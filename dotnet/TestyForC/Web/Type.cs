using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestyForC.Web
{
    public class Type
    {
        private String group;
        private String value;

        public Type(string group, string value)
        {
            this.group = group;
            this.value = value;
        }

        public string Group { get => group; set => group = value; }
        public string Value { get => value; set => this.value = value; }
    }
}
