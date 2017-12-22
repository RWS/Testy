// For extjs 6 grid
var c = Ext.getCmp('terminologyexcelinportstructuretree-1096');

//scrollTop
(function (c) {
    var a = c.view.scrollable._scrollElement;
    if (a.dom.scrollTop != 0) {
        a.dom.scrollTop = 0;
        return true
    }
    return false
})(c)

//scrollBottom
(function (c) {
    var b = c.view.scrollable.getMaxUserPosition().y;
    c.view.scrollBy(0, b);
    setTimeout(function () {
        c.view.scrollBy(0, 1000);
    }, 50);
    return true
})(c)

//scrollPageDown
(function (c) {
    var a = c.view,
        b = a.scrollable._scrollElement;
    if (b.dom.scrollTop < a.scrollable.getMaxPosition().y) {
        b.dom.scrollTop += a.getHeight() - 13;
        return true
    }
    return false
})(c)

//scrollPageDownInTree
(function (c) {
    var a = c.view,
        b = a.scrollable._scrollElement;
    if (b.dom.scrollTop < a.scrollable.getMaxPosition().y) {
        b.dom.scrollTop += (a.body.dom.childElementCount * a.body.dom.firstChild.scrollHeight) * 2 - a.body.dom.firstChild.scrollHeight;
        return true
    }
    return false
})(c)

//scrollPageUp
(function (c) {
    var a = c.view,
        b = a.scrollable._scrollElement;
    if (b.dom.scrollTop > 0) {
        b.dom.scrollTop -= a.getHeight() - 13;
        return true
    }
    return false
})(c)

//isScrollButton
(function (c) {
    var a = c.view.scrollable,
        b = a._scrollElement;
    return b.dom.scrollTop >= a.getMaxPosition().y;
})(c)


//isScrollTop
(function (c) {
    return c.view.scrollable._scrollElement.dom.scrollTop == 0;
})(c)

