// For extjs 6 grid
var c = Ext.getCmp('terminologylanguagesgrid-1243');

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
        b = a.scrollable._scrollElement,
        d = a.body.dom.firstChild.scrollHeight;
    if (b.dom.scrollTop < a.scrollable.getMaxPosition().y) {
        b.dom.scrollTop += (a.body.dom.childElementCount - 3) * d;
        setTimeout(function () {
            b.dom.scrollTop += 1;
        }, 1);
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
    return Math.round(b.dom.scrollTop) >= a.getMaxPosition().y;
})(c)


//isScrollTop
(function (c) {
    return c.view.scrollable._scrollElement.dom.scrollTop == 0;
})(c)

//scrollBy(x,y)
(function (c) {
    c.view.scrollable._scrollElement.dom.scrollBy(0,100);
})(c)

(function (c) {
    var top = c.scrollable._scrollElement.dom.scrollTop;
    c.scrollBy(0,100);
    var topTemp = c.scrollable._scrollElement.dom.scrollTop;
    return top < topTemp;
})(c)
