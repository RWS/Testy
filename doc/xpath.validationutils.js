//This function counts the numbered of elements that match the given xpath
(function(xPath){

	var xPathRes = document.evaluate(xPath, document, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);

	var actualNode = xPathRes.iterateNext();
	var count = 0;
	while(actualNode) {
		count++;
		actualNode = xPathRes.iterateNext();
	}

	return count;
})("//*")

//compressed:(function(t){for(var e=document.evaluate(t,document,null,XPathResult.ORDERED_NODE_ITERATOR_TYPE,null),u=e.iterateNext(),n=0;u;)n++,u=e.iterateNext();return n})("//*")

//This function lists all elements that match the given xpath
(function(xPath){

	var xPathRes = document.evaluate(xPath, document, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);

	var message = ""

	var actualNode = xPathRes.iterateNext();
	while(actualNode) {
		message += actualNode.outerHTML + '\n';
		actualNode = xPathRes.iterateNext();
	}

	return message;
})("//*")

//(function(e){for(var n=document.evaluate(e,document,null,XPathResult.ORDERED_NODE_ITERATOR_TYPE,null),t="",l=n.iterateNext();l;)t+=l.outerHTML+"\n",l=n.iterateNext();return t})("//*")

