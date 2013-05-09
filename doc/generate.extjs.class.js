
var _classGen = {
    logId: false,
    elemCount: 0,

    getImports: function(classCode){
        // TODO optimize imports
        var imports = [
            'package com.sdl;\n',

            'import com.extjs.selenium.button.Button;',
            'import com.extjs.selenium.form.Checkbox;',
            'import com.extjs.selenium.form.ComboBox;',
            'import com.extjs.selenium.form.TextField;',
            'import com.extjs.selenium.grid.EditorGridPanel;',
            'import com.extjs.selenium.panel.Panel;',
            'import com.extjs.selenium.window.Window;'
        ];
        return imports.join('\n') + '\n\n';
    },

    getVarName: function(name){
        name = name ? name : 'item' + (_classGen.elemCount++);
        name = Ext.util.Format.stripTags(name);
        // remove spaces and capitalize each word
        var words = name.split(/\s/);
        Ext.each(words, function(w, i){
            words[i] = Ext.util.Format.capitalize(w);
        });
        name = words.join('');

        name = name.replace(/[\(\)/\\:;\<>=\-",+&]/gi, '');
        //name = Ext.util.Format.capitalize(name);
        return !name ? name : name.charAt(0).toLowerCase() + name.substr(1);
    },

    getPanelConfig: function (item){
        var code = '',
            label;
        if(item.title){
            code += '.setTitle("' + Ext.util.Format.stripTags(item.title) + '")';
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

    getButtonConfig: function (item){
        var code = '',
            text = item.text;
        if(text){
            code += '.setText("' + text + '")';
        } else if (item.iconCls){
            code += '.setCls("' + item.iconCls + '")';
        }
        return code;
    },

    getItemCode: function (container, item){
        var me = this,
            name = '',
            className = '',
            classConstructor = '',
            code = '\tpublic ',
            xtype = item.getXType ? item.getXType() : ('unknownGetXType'),
            xtypes = item.getXTypes ? item.getXTypes() : ('unknownGetXTypes');

        //console.debug('getItemCode', container, item);

        // all classes that are or extend panels
        if(me.isTypeOf(xtype, xtypes, 'panel') || item instanceof Ext.Panel){
            if(me.isTypeOf(xtype, xtypes, 'window') || item instanceof Ext.Window){
                className = 'Window';
            } else if(me.isTypeOf(xtype, xtypes, 'editorgrid') || item instanceof Ext.grid.EditorGridPanel){
                className = 'EditorGridPanel';
            } else if(me.isTypeOf(xtype, xtypes, 'grid') || item instanceof Ext.grid.GridPanel){
                className = 'GridPanel';
            } else {
                className = 'Panel';
            }
            name = item.title || item.fieldLabel;
            name = _classGen.getVarName(name) + className;
            code += className + ' ' + name + ' = new ' + className + '(' + container + ')';
            classConstructor = _classGen.getPanelConfig(item);
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
                classConstructor = _classGen.getFieldConfig(item);
            } else {
                code = '';
                console.warn('no field className found', container, item, xtype, xtypes, item.id);
            }
        } else if(xtype == 'button' || xtypes.indexOf('/button/') != -1){
            name = _classGen.getVarName(item.text || item.tooltip) + 'Button';
            code += 'Button ' + name + ' = new Button(' + container + ')';
            classConstructor = _classGen.getButtonConfig(item);
        } else if(!_classGen.isNecesaryType(xtype, xtypes)){
            code = '';
        } else {
            //code += 'XYZ xyz = new XYZ("' + xtype + '", "' + xtypes + '")';
            code = '';
            console.warn('no classified xtype found', container, item, xtype, xtypes, item.id);
        }
        if(code != ''){
            code += classConstructor;
            code += ';';
            if(_classGen.logId){
                code += '// ' + item.id;
            }
            code += '\n';
        }
        return {
            code: code,
            name: name,
            className: className,
            classConstructor: classConstructor
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
        if(item.getXType){
            return item.getXType() == 'tabpanel' || item.getXTypes().indexOf('/tabpanel/') != -1;
        } else {
            console.warn('item does not have getXType() method', item);
            return false;
        }
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

    getCmpClassCode : function(component) {
        var elements,
            name,
            code = '',
            container = 'this';

        if(typeof component === 'string'){
            component = Ext.getCmp(component);
        }
        if(!component){
            return 'no component found';
        }
        elements = _classGen.getItemCode('', component);

        name = elements.name;
        name = name.charAt(0).toUpperCase() + name.substr(1);
        code += 'public class ' + name + ' extends ' + elements.className + ' {\n';
        // constructor
        code += '\tpublic ' + name + '(){\n';
        code += '\t\t' + (elements.classConstructor ? ('this' + elements.classConstructor + ';') : '') + '\n';
        code += '\t}\n';

        code += _classGen.getInsideItems(container, component, false);

        code += '\n// methods hire \n';
        code += '\n}\n';

        code = _classGen.getImports(code) + code;

        return code;
    },

    getActiveWinClassCode : function (){
        return _classGen.getCmpClassCode(Ext.WindowMgr.getActive());
    }
};

// ================
// usage examples:
// ================

console.info('\n'+ _classGen.getActiveWinClassCode());
//console.info('\n'+ _classGen.getCmpClassCode('winUserPreferences'));

// TODO verify that all var names are used only once