var c = Ext.getCmp('grid-1009');

(function (c) {
    var a = c.view,
        b = a.getScrollable()._scrollElement;
    console.log('body.getHeight=', a.body.getHeight());
    console.log('b.getHeight=', b.getHeight());
    console.log('b.dom.scrollTop=', b.dom.scrollTop);
    if (b.dom.scrollTop < (a.body.getHeight() - b.getHeight())) {
        b.dom.scrollTop += b.getHeight() - 10;
        return true
    }
    return false
})(c)

//scrollTop
var c = Ext.getCmp('grid-1009');
(function (g) {
    var a = g.view.getScrollable()._scrollElement;
    if (a.dom.scrollTop != 0) {
        a.dom.scrollTop = 0;
        return true
    }
    return false
})(c)

//scrollBottom
(function (g) {
        var a = g.view.getScrollable()._scrollElement,
            b = g.view.getScrollY();
    console.log('b=', b);
        a.dom.scrollTop = b;
        return true
    }
)(c)
