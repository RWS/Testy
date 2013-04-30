// TODO in EditorGridPanel add clickToEdit option new EditorGridPanel(this).setClicksToEdit(2)
// TODO fieldLabel must have ":" have labelSeparator

var _classGen = {
    logId: true,
    elemCount: 0,

    getVarName: function(name){
        name = name ? name : 'item' + (_classGen.elemCount++);
        // remove spaces and capitalize each word
        var words = name.split(/\s/);
        Ext.each(words, function(w, i){
            words[i] = Ext.util.Format.capitalize(w);
        });
        name = words.join('');

        name = name.replace(/[\(\)/\\:;\<>=\-"]/gi, '');
        //name = Ext.util.Format.capitalize(name);
        return !name ? name : name.charAt(0).toLowerCase() + name.substr(1);
    },

    getPanelConfig: function (item){
        var code = '';
        if(item.title){
            code += '.setTitle("' + item.title + '")';
        } else if(item.fieldLabel){
            code += '.setLabel("' + item.fieldLabel + '")';
        }
        return code;
    },

    getItemCode: function (container, item){
        var name = '',
            code = '\tpublic ',
            xtype = item.getXType(),
            xtypes = item.getXTypes(),
            label = item.fieldLabel + (item.ownerCt.getLayout().labelSeparator || '');

        //console.debug('getItemCode', container, item);
        if(xtype == 'editorgrid' || xtypes.indexOf('/editorgrid/') != -1 || item instanceof Ext.grid.EditorGridPanel){
            name = _classGen.getVarName(item.title) + 'EditorGridPanel';
            code += 'EditorGridPanel ' + name + ' = new EditorGridPanel(' + container + ')';
            code += _classGen.getPanelConfig(item);
        } else if(xtype == 'grid' || xtypes.indexOf('/grid/') != -1 || item instanceof Ext.grid.GridPanel){
            name = _classGen.getVarName(item.title) + 'GridPanel';
            code += 'GridPanel ' + name + ' = new GridPanel(' + container + ')';
            code += _classGen.getPanelConfig(item);
        } else if(xtype == 'panel' || xtypes.indexOf('/panel/') != -1 || item instanceof Ext.Panel){
            console.debug('panel item:', item.id, item);
            name = _classGen.getVarName(item.title) + 'Panel';
            code += 'Panel ' + name + ' = new Panel(' + container + ')';
            code += _classGen.getPanelConfig(item);
        } else if(xtype == 'combo' || xtypes.indexOf('/combo/') != -1){
            name = _classGen.getVarName(label) + 'ComboBox';
            code += 'ComboBox ' + name + ' = new ComboBox(' + container + ', "' + label +  '")';
        } else if(xtype == 'textarea' || xtypes.indexOf('/textarea/') != -1){
            name = _classGen.getVarName(label) + 'TextArea';
            code += 'TextArea ' + name + ' = new TextArea(' + container + ', "'  + label +  '")';
        } else if(xtype == 'textfield' || xtypes.indexOf('/textfield/') != -1){
            name = _classGen.getVarName(label) + 'TextField';
            code += 'TextField ' + name + ' = new TextField(' + container + ', "'  + label +  '")';
        } else if(xtype == 'displayfield' || xtypes.indexOf('/displayfield/') != -1){
            name = _classGen.getVarName(label) + 'DisplayField';
            code += 'DisplayField ' + name + ' = new DisplayField(' + container + ', "'  + label +  '")';
        } else if(xtype == 'checkbox' || xtypes.indexOf('/checkbox/') != -1){
            name = _classGen.getVarName(label || item.boxLabel) + 'Checkbox';
            code += 'Checkbox ' + name + ' = new Checkbox(' + container + ', "'  + (label || item.boxLabel) +  '")';
        } else if(xtype == 'button' || xtypes.indexOf('/button/') != -1){
            name = _classGen.getVarName(item.text) + 'Button';
            code += 'Button ' + name + ' = new Button(' + container + ', "'  + item.text +  '")';
        } else if(!_classGen.isNecesaryType(xtype, xtypes)){
            code = '';
        } else {
            //code += 'XYZ xyz = new XYZ("' + xtype + '", "' + xtypes + '")';
            code = '';
            console.warn(container, item, xtype, xtypes, item.id);
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
        var necesary = true;
        if(xtype == 'hidden' || xtypes.indexOf('/hidden/') != -1){
            necesary = false;
        } else if(xtype == 'tbfill' || xtypes.indexOf('/tbfill/') != -1){
            necesary = false;
        } else if(xtype == 'tbtext' || xtypes.indexOf('/tbtext/') != -1){
            necesary = false;
        } else if(xtype == 'tbspacer' || xtypes.indexOf('/tbspacer/') != -1){
            necesary = false;
        } else if(xtype == 'tbseparator' || xtypes.indexOf('/tbseparator/') != -1){
            necesary = false;
        } else if(xtype == 'box' || xtypes.indexOf('/box/') != -1){
            necesary = false; // TODO this is temporary
        }
        return necesary;
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