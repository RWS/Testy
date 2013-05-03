
var _classGen = {
    logId: false,
    elemCount: 0,

    getVarName: function(name){
        name = name ? name : 'item' + (_classGen.elemCount++);
        // remove spaces and capitalize each word
        var words = name.split(/\s/);
        $.each(words, function(i, w){
            words[i] = !w ? w : w.charAt(0).toUpperCase() + w.substr(1).toLowerCase();
        });
        name = words.join('');

        name = name.replace(/[\(\)/\\:;\<>=\-",+]/gi, '');
        //name = Ext.util.Format.capitalize(name);
        return !name ? name : name.charAt(0).toLowerCase() + name.substr(1);
    },

    getPanelConfig: function (item){
         var code = '';
        if(item.text()){
            code += '.setLabel("' + item.text() + '")';
        } else if(item.id){
            code += '.setId("' + item.id + '")';
        }
        return code;
    },

    getFieldConfig: function (item){
        var code = '';

        if(item.prop("id")){
            code += '.setId("' + item.prop("id") + '")';
        } else if (item.boxLabel){
            code += '.setLabel("' + item.boxLabel + '")';
        } else if (item.name){
            code += '.setName("' + item.name + '")';
        }
        return code;
    },

    getButtonConfig: function (item){
        var code = '';
        if(item.prop("id")){
            code += '.setId("' + item.prop("id") + '")';
        } else if (item.text()){
            code += '.setText("' + item.text() + '")';
        }
        return code;
    },

    getItemCode: function (container, item){
        var me = this,
            name = '',
            className = '',
            code = '\tpublic ',
            tag = item.prop("tagName").toLowerCase();

        // all classes that are or extend panels
        if(false){
            if(me.isTypeOf(xtype, xtypes, 'editorgrid') || item instanceof Ext.grid.EditorGridPanel){
                className = 'EditorGridPanel';
            } else if(me.isTypeOf(xtype, xtypes, 'grid') || item instanceof Ext.grid.GridPanel){
                className = 'GridPanel';
            } else {
                className = 'Panel';
            }
            name = item.title || item.fieldLabel;
            name = _classGen.getVarName(name) + className;
            code += className + ' ' + name + ' = new ' + className + '(' + container + ')';
            code += _classGen.getPanelConfig(item);
        } else if(tag == 'input'){
            var type = item.prop("type");
            if(type == 'text' || type == 'email'){
                className = 'SimpleTextField';
            } else if(type == 'checkbox'){
                className = 'SimpleCheckbox';
            }
            if(className){
                name = item.prop("id"); // create order for variable name
                name = _classGen.getVarName(name) + className;
                code += className + ' ' + name + ' = new ' + className + '()';
                code += _classGen.getFieldConfig(item);
            } else {
                code = '';
                console.warn('no field className found', container, tag, item.id);
            }
        } else if(tag == 'button'){

            if(item.text()){
                className = 'SimpleButton';
            }
            name = item.prop("id"); // create order for variable name
            name = _classGen.getVarName(name) + className;
            code += className + ' ' + name + ' = new ' + className + '()';
            code += _classGen.getButtonConfig(item);
        } else if(false){
            code = '';
        } else {
            //code += 'XYZ xyz = new XYZ("' + xtype + '", "' + xtypes + '")';
            code = '';
            console.warn('no classified xtype found', container, tag, item.id);
        }
        if(code != ''){
            code += ';';
            if(_classGen.logId){
                code += '// ' + item.id;
            }
            code += '\n';
        }
        return {
            code: code,
            name: name
        };
    },

    isNecesaryType : function (xtype, xtypes){
        return true;
        var me = this,
            necesary = true;
        if(me.isTypeOf(xtype, xtypes, 'hidden')){
            necesary = false;
        } else if(me.isTypeOf(xtype, xtypes, 'tbfill')){
            necesary = false;
        } else if(me.isTypeOf(xtype, xtypes, 'tbtext')){
            necesary = false;
        } else if(me.isTypeOf(xtype, xtypes, 'tbseparator')){
            necesary = false;
        } else if(me.isTypeOf(xtype, xtypes, 'tbseparator')){
            necesary = false;
        } else if(me.isTypeOf(xtype, xtypes, 'box')){
            necesary = false; // TODO this is temporary
        }
        return necesary;
    },

    isTypeOf: function(xtype, xtypes, type){
        return xtype == type;
    },

    getEachElementsCode : function (container, items){
        var code = '';
        if(items){
            items.each(function(i, e){
                code += _classGen.getItemsCode(container, $(e));
            });
        }

        return code;
    },

    getItemsCode: function (container, item){
        //console.debug('getItemsCode', container, item);
        var elements,
            code = '';

        elements = _classGen.getItemCode(container, item);
        code += elements.code;
        container = elements.name || container;

        code += _classGen.getInsideItems(container, item);

        return code;
    },

    getInsideItems: function(container, item){
        var code = '';
        code += _classGen.getEachElementsCode(container, $(item).children());
        return code;
    },

    getBadyClassCode : function (){
        return _classGen.getElClassCode($("body").children().first());
    },

    getElClassCode : function (el){
        var
            name = _classGen.getVarName(el.title),
            code,
            container = 'this';
        code = 'public class ' + name + 'MyWebLocator extends WebLocator {\n';
        //code += '\tpublic ' + name + 'Window(){\n';
        //code += '\t\tsetTitle("' + w.title + '");\n';
        //code += '\t}\n';

        code += _classGen.getInsideItems(container, el);

        code += '}\n';
        return code;
    }
};

console.info('\n'+ _classGen.getBadyClassCode());
