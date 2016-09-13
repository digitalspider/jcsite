function getBlogs(ctx, tags, maxRows, templateEleName, blogEleName) {
	var formURL = ctx+"/public/blog";
	if (tags!=null) {
		formURL = formURL + "/"+tags;
	}
	if (maxRows!=null) {
		formURL = formURL + "/"+maxRows;
	}
	if (blogEleName==null) {
		blogEleName = "#blogs";
	}
	$.post(formURL, null, function (data) {
		var ele = $(blogEleName);
		if (data.length>0) {
			//var backup = $.extend( true, {}, ele );
			ele.empty(); // remove old values
			var template = $(templateEleName).html();
			Mustache.parse(template);   // optional, speeds up future uses
			var rendered = Mustache.render(template, {blog: data});
			ele.html(rendered);
			/*
			$.each(data, function(i, blog) {
				ele.append($("<div></div>").attr("class", "col-xs-12 col-sm-6 col-md-6 col-lg-3 col-xl-3")
					.append($("<div></div>").attr("class", "tm-content-box")
						.append($("<h4></h4>").attr("class", "tm-margin-b-20 tm-gold-text").text(blog.title))
						.append($("<p></p>").attr("class", "tm-margin-b-20").html(blog.description))
						.append($("<a></a>").attr("href", blog.link).attr("class","tm-btn text-uppercase").text("Read more"))
					)
				);
			});
			*/
		}
	}, 'json');
}