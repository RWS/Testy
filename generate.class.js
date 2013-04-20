// TODO in EditorGridPanel add clickToEdit option new EditorGridPanel(this).setClicksToEdit(2)

function getVarName(name){
    name = name ? name.replace(/\s/g, '') : 'name';
    name = name.replace(/[\(\)/\\:;\<>=\-"]/gi, '');
    name = Ext.util.Format.capitalize(name);
    return !name ? name : name.charAt(0).toLowerCase() + name.substr(1);
}

function getItemCode(container, item){
    var code = '\tpublic ',
        xtype = item.getXType(),
        xtypes = item.getXTypes(),
        label = item.fieldLabel;

    //console.debug('getItemCode', container, item);
    if(xtype == 'editorgrid' || xtypes.indexOf('/editorgrid/') != -1 || item instanceof Ext.grid.EditorGridPanel){
        code += 'EditorGridPanel ' + getVarName(item.title) + 'EditorGridPanel = new EditorGridPanel(' + container + ', "' + (item.title || label) +  '")';
    } else if(xtype == 'grid' || xtypes.indexOf('/grid/') != -1 || item instanceof Ext.grid.GridPanel){
        code += 'GridPanel ' + getVarName(item.title) + 'GridPanel = new GridPanel(' + container + ', "' + item.title +  '")';
    } else if(xtype == 'panel' || xtypes.indexOf('/panel/') != -1 || item instanceof Ext.Panel){
        code += 'Panel ' + getVarName(item.title) + 'Panel = new Panel(' + container + ', "' + item.title +  '")';
    } else if(xtype == 'combo' || xtypes.indexOf('/combo/') != -1){
        code += 'ComboBox ' + getVarName(label) + 'ComboBox = new ComboBox(' + container + ', "' + label +  '")';
    } else if(xtype == 'textfield' || xtypes.indexOf('/textfield/') != -1){
        code += 'TextField ' + getVarName(label) + 'TextField = new TextField(' + container + ', "'  + label +  '")';
    } else if(xtype == 'displayfield' || xtypes.indexOf('/displayfield/') != -1){
        code += 'DisplayField ' + getVarName(label) + 'DisplayField = new DisplayField(' + container + ', "'  + label +  '")';
    } else if(xtype == 'checkbox' || xtypes.indexOf('/checkbox/') != -1){
        code += 'Checkbox ' + getVarName(label) + 'Checkbox = new Checkbox(' + container + ', "'  + (label || item.boxLabel) +  '")';
    } else if(xtype == 'button' || xtypes.indexOf('/button/') != -1){
        code += 'Button ' + getVarName(item.text) + 'Button = new Button(' + container + ', "'  + item.text +  '")';
    } else if(!isNecesaryType(xtype, xtypes)){
        code = '';
    } else {
        //code += 'XYZ xyz = new XYZ("' + xtype + '", "' + xtypes + '")';
		code = '';
        console.warn(container, item, xtype, xtypes);
    }
    if(code != ''){
        code += ';\n';
    }
    return code;
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
                if(isTab){
                    code += 'public TabPanel ' + getVarName(it.title) + 'TabPanel = new TabPanel(' + container + ', "' + it.title +  '");\n';
                }
                code += getItemsCode(container, it);
            });
        }
    }

    return code;
}

function getItemsCode(container, item){
    //console.debug('getItemsCode', container, item);
    var code = '',
        tbar = item.getTopToolbar ? item.getTopToolbar() : false,
        bbar = item.getBottomToolbar ? item.getBottomToolbar(): false;

    var isTab = isTabPanel(item);
    if(!isTab){
        code += getItemCode(container, item);
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
    code = 'public class ' + name + 'Window {\n';
    w.items.each(function(it){
        code += getItemsCode('this', it);
    });
    code += '}\n';
    return code;
}


console.info('\n'+ getActiveWinClassCode());