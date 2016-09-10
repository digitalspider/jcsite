$(document).ready(function () {
	getLinks("home", 5, null);
});

function getLinks(tags, maxRows, blogEleName) {
	var formURL = "/website/public/link";
	if (tags!=null) {
		formURL = formURL + "/"+tags;
	}
	if (maxRows!=null) {
		formURL = formURL + "/"+maxRows;
	}
	if (blogEleName==null) {
		blogEleName = "#links";
	}
	$.post(formURL, null, function (data) {
		var ele = $(blogEleName);
		if (data.length>0) {
			//var backup = $.extend( true, {}, ele );
			ele.empty(); // remove old values
			$.each(data, function(i, blog) {
				ele.append($("<li></li>")
					.append($("<a></a>").attr("href", link.link).attr("class","tm-text-link").text(link.title)));
			});
		}
	}, 'json');
}