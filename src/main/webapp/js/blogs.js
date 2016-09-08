$(document).ready(function () {
	var formURL = "/website/public/blog";
	$.getJSON(formURL, null, function (data) {
		var ele = $("#blogs");
		if (data.length>0) {
			ele.empty(); // remove old values
			$.each(data, function(i, blog) {
				ele.append($("<div></div>").attr("class", "col-xs-12 col-sm-6 col-md-6 col-lg-3 col-xl-3")
					.append($("<div></div>").attr("class", "tm-content-box")
						.append($("<h4></h4>").attr("class", "tm-margin-b-20 tm-gold-text").text(blog.title))
						.append($("<p></p>").attr("class", "tm-margin-b-20").html(blog.description))
						.append($("<a></a>").attr("href", blog.link).attr("class","tm-btn text-uppercase").text("Read more"))
					)
				);
			});
		}
	});
});