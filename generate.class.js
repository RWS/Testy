// TODO in EditorGridPanel add clickToEdit option new EditorGridPanel(this).setClicksToEdit(2)
// TODO fieldLabel must have ":" have labelSeparator

var _classGen = {
    logId: false,
    elemCount: 0,

    getVarName: function(name){
        name = name ? name : 'item' + (_classGen.elemCount++);
        // remove spaces and capitalize each word
        var words = name.split(/\s/);
        Ext.each(words, function(w, i){
            words[i] = Ext.util.Format.capitalize(w);
        });
        name = words.join('');

        name = name.replace(/[\(\)/\\:;\<>=\-",]/gi, '');
        //name = Ext.util.Format.capitalize(name);
        return !name ? name : name.charAt(0).toLowerCase() + name.substr(1);
    },

    getPanelConfig: function (item){
        var code = '',
            label;
        if(item.title){
            code += '.setTitle("' + item.title + '")';
        } else if(item.fieldLabel){
            label = item.fieldLabel;
            label += item.ownerCt.getLayout().labelSeparator || '';
            code += '.setLabel("' + label + '")';
        }
        return code;
    },

    getFieldConfig: function (item){
        var code = '',
            label = item.fieldLabel;
        if(label){
            label += item.ownerCt.getLayout().labelSeparator || '';
            code += '.setLabel("' + label + '")';
        } else if (item.boxLabel){
            code += '.setLabel("' + item.boxLabel + '")';
        } else if (item.name){
            code += '.setName("' + item.name + '")';
        }
        return code;
    },

    getItemCode: function (container, item){
        var me = this,
            name = '',
            className = '',
            code = '\tpublic ',
            xtype = item.getXType(),
            xtypes = item.getXTypes();

        //console.debug('getItemCode', container, item);

        // all classes that are or extend panels
        if(me.isTypeOf(xtype, xtypes, 'panel') || item instanceof Ext.Panel){
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
        } else if(xtype == 'field' || xtypes.indexOf('/field/') != -1 || item instanceof Ext.form.Field){
            if(me.isTypeOf(xtype, xtypes, 'combo')){
                className = 'ComboBox';
            } else if(me.isTypeOf(xtype, xtypes, 'textarea')){
                className = 'TextArea';
            } else if(me.isTypeOf(xtype, xtypes, 'textfield')){
                className = 'TextField';
            } else if(me.isTypeOf(xtype, xtypes, 'displayfield')){
                className = 'DisplayField';
            } else if(me.isTypeOf(xtype, xtypes, 'checkbox')){
                className = 'Checkbox';
                //TODO code += (label || item.boxLabel)
            }
            if(className){
                name = item.fieldLabel || item.boxLabel || item.name; // create order for variable name
                name = _classGen.getVarName(name) + className;
                code += className + ' ' + name + ' = new ' + className + '(' + container + ')';
                code += _classGen.getFieldConfig(item);
            } else {
                code = '';
                console.warn('no field className found', container, item, xtype, xtypes, item.id);
            }
        } else if(xtype == 'button' || xtypes.indexOf('/button/') != -1){
            name = _classGen.getVarName(item.text) + 'Button';
            code += 'Button ' + name + ' = new Button(' + container + ', "'  + item.text +  '")';
        } else if(!_classGen.isNecesaryType(xtype, xtypes)){
            code = '';
        } else {
            //code += 'XYZ xyz = new XYZ("' + xtype + '", "' + xtypes + '")';
            code = '';
            console.warn('no classified xtype found', container, item, xtype, xtypes, item.id);
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
        return xtype == type || xtypes.indexOf('/' + type + '/') != -1;
    },

    isTabPanel : function (item){
        return item.getXType() == 'tabpanel' || item.getXTypes().indexOf('/tabpanel/') != -1;
    },

    getEachElementsCode : function (container, items, isTab){
        var code = '';
        if(items){
            if (Ext.isArray(items)){
                for(var i = 0, len = items.length; i < len; i++){
                    code += _classGen.getItemsCode(container, items[i]);
                }
            } else {
                items.each(function(it){
                    var c = container;
                    if(isTab){
                        c = _classGen.getVarName(it.title) + 'TabPanel';
                        code += '\tpublic TabPanel ' + c + ' = new TabPanel(' + container + ', "' + it.title +  '");\n';
                    }
                    code += _classGen.getItemsCode(c, it);
                });
            }
        }

        return code;
    },

    getItemsCode: function (container, item){
        //console.debug('getItemsCode', container, item);
        var elements,
            code = '';

        var isTab = _classGen.isTabPanel(item);
        if(!isTab){
            elements = _classGen.getItemCode(container, item);
            code += elements.code;
            container = elements.name || container;
        }

        code += _classGen.getInsideItems(container, item, isTab);

        return code;
    },

    getInsideItems: function(container, item, isTab){
        var code = '',
            tbar = item.getTopToolbar ? item.getTopToolbar() : false,
            bbar = item.getBottomToolbar ? item.getBottomToolbar(): false,
            fbar = item.getFooterToolbar ? item.getFooterToolbar(): false;

        code += _classGen.getEachElementsCode(container, item.items, isTab);
        if(tbar){
            code += _classGen.getEachElementsCode(container, tbar.items, false);
        }
        if(bbar){
            code += _classGen.getEachElementsCode(container, bbar.items, false);
        }
        if(fbar){
            code += _classGen.getEachElementsCode(container, fbar.items, false);
        }
        return code;
    },

    getActiveWinClassCode : function (){
        var w = Ext.WindowMgr.getActive(),
            name = _classGen.getVarName(w.title),
            code,
            container = 'this';
        console.debug('getActiveWinClassCode', name);
        code = 'public class ' + name + 'Window extends Window {\n';
        code += '\tpublic ' + name + 'Window(){\n';
        code += '\t\tsetTitle("' + w.title + '");\n';
        code += '\t}\n';

        code += _classGen.getInsideItems(container, w, false);

        code += '}\n';
        return code;
    }
};

console.info('\n'+ _classGen.getActiveWinClassCode());
