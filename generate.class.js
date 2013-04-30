// TODO in EditorGridPanel add clickToEdit option new EditorGridPanel(this).setClicksToEdit(2)
// TODO fieldLabel must have ":" have labelSeparator

var logId = false;
var elemCount = 0;
function getVarName(name){
    name = name ? name : 'item' + (elemCount++);
    // remove spaces and capitalize each word
    var words = name.split(/\s/);
    Ext.each(words, function(w, i){
        words[i] = Ext.util.Format.capitalize(w);
    });
    name = words.join('');

    name = name.replace(/[\(\)/\\:;\<>=\-"]/gi, '');
    //name = Ext.util.Format.capitalize(name);
    return !name ? name : name.charAt(0).toLowerCase() + name.substr(1);
}

function getPanelConfig(item){
    var code = '';
    if(item.title){
        code += '.setTitle("' + item.title + '")';
    } else if(item.fieldLabel){
        code += '.setLabel("' + item.fieldLabel + '")';
    }
    return code;
}

function getItemCode(container, item){
    var name = '',
        code = '\tpublic ',
        xtype = item.getXType(),
        xtypes = item.getXTypes(),
        label = item.fieldLabel;

    //console.debug('getItemCode', container, item);
    if(xtype == 'editorgrid' || xtypes.indexOf('/editorgrid/') != -1 || item instanceof Ext.grid.EditorGridPanel){
        name = getVarName(item.title) + 'EditorGridPanel';
        code += 'EditorGridPanel ' + name + ' = new EditorGridPanel(' + container + ')';
        code += getPanelConfig(item);
    } else if(xtype == 'grid' || xtypes.indexOf('/grid/') != -1 || item instanceof Ext.grid.GridPanel){
        name = getVarName(item.title) + 'GridPanel';
        code += 'GridPanel ' + name + ' = new GridPanel(' + container + ')';
        code += getPanelConfig(item);
    } else if(xtype == 'panel' || xtypes.indexOf('/panel/') != -1 || item instanceof Ext.Panel){
        console.debug('panel item:', item.id, item);
        name = getVarName(item.title) + 'Panel';
        code += 'Panel ' + name + ' = new Panel(' + container + ')';
        code += getPanelConfig(item);
    } else if(xtype == 'combo' || xtypes.indexOf('/combo/') != -1){
        name = getVarName(label) + 'ComboBox';
        code += 'ComboBox ' + name + ' = new ComboBox(' + container + ', "' + label +  '")';
    } else if(xtype == 'textarea' || xtypes.indexOf('/textarea/') != -1){
        name = getVarName(label) + 'TextArea';
        code += 'TextArea ' + name + ' = new TextArea(' + container + ', "'  + label +  '")';
    } else if(xtype == 'textfield' || xtypes.indexOf('/textfield/') != -1){
        name = getVarName(label) + 'TextField';
        code += 'TextField ' + name + ' = new TextField(' + container + ', "'  + label +  '")';
    } else if(xtype == 'displayfield' || xtypes.indexOf('/displayfield/') != -1){
        name = getVarName(label) + 'DisplayField';
        code += 'DisplayField ' + name + ' = new DisplayField(' + container + ', "'  + label +  '")';
    } else if(xtype == 'checkbox' || xtypes.indexOf('/checkbox/') != -1){
        name = getVarName(label || item.boxLabel) + 'Checkbox';
        code += 'Checkbox ' + name + ' = new Checkbox(' + container + ', "'  + (label || item.boxLabel) +  '")';
    } else if(xtype == 'button' || xtypes.indexOf('/button/') != -1){
        name = getVarName(item.text) + 'Button';
        code += 'Button ' + name + ' = new Button(' + container + ', "'  + item.text +  '")';
    } else if(!isNecesaryType(xtype, xtypes)){
        code = '';
    } else {
        //code += 'XYZ xyz = new XYZ("' + xtype + '", "' + xtypes + '")';
		code = '';
        console.warn(container, item, xtype, xtypes, item.id);
    }
    if(code != ''){
        code += ';';
        if(logId){
            code += '// ' + item.id;
        }
        code += '\n';
    }
    return {
        code: code,
        name: name
    };
}

function isNecesaryType(xtype, xtypes){
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
}

function isTabPanel(item){
    return item.getXType() == 'tabpanel' || item.getXTypes().indexOf('/tabpanel/') != -1;
}

function getEachElementsCode(container, items, isTab){
    var code = '';
    if(items){
        if (Ext.isArray(items)){
            for(var i = 0, len = items.length; i < len; i++){
                code += getItemsCode(container, items[i]);
            }
        } else {
            items.each(function(it){
                var c = container;
                if(isTab){
                    c = getVarName(it.title) + 'TabPanel';
                    code += '\tpublic TabPanel ' + c + ' = new TabPanel(' + container + ', "' + it.title +  '");\n';
                }
                code += getItemsCode(c, it);
            });
        }
    }

    return code;
}

function getItemsCode(container, item){
    //console.debug('getItemsCode', container, item);
    var elements,
        code = '',
        tbar = item.getTopToolbar ? item.getTopToolbar() : false,
        bbar = item.getBottomToolbar ? item.getBottomToolbar(): false;

    var isTab = isTabPanel(item);
    if(!isTab){
        elements = getItemCode(container, item);
        code += elements.code;
        container = elements.name || container;
    }
    code += getEachElementsCode(container, item.items, isTab);
    if(tbar){
        code += getEachElementsCode(container, tbar.items, false);
    }
    if(bbar){
        code += getEachElementsCode(container, bbar.items, false);
    }

    return code;
}

function getActiveWinClassCode(){
    var w = Ext.WindowMgr.getActive(),
        name = getVarName(w.title),
        code;
    console.debug('getActiveWinClassCode', name);
    code = 'public class ' + name + 'Window extends Window {\n';
    code += '\tpublic ' + name + 'Window(){\n';
    code += '\t\tsetTitle("' + w.title + '");\n\t}\n';
    w.items.each(function(it){
        code += getItemsCode('this', it);
    });
    code += '\t}\n';
    return code;
}


console.info('\n'+ getActiveWinClassCode());