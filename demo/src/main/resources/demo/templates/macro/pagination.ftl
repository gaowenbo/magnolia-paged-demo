
[#-- Definition: Pagination --]
[#macro pagination pager position]

    [#if pager?has_content && (pager.position?starts_with(position) || pager.position?starts_with("both"))]
        [#if pager.numPages > 1]
        
        	<div class="page">
				<div class="page">
					[#if pager.currentPage > 1]
					<a href="${pager.getPageLink(pager.currentPage -1)}"><img src="resources/templating-kit/themes/pop/img/icons/previousPage.png" class="leftImg">上一页</a>
					[/#if]
					
					[#if pager.beginIndex > 1]
	                    <a href="${pager.getPageLink(1)}">1</a>
	                    [#if pager.beginIndex > 2]
	                    	<span>...</span>
	                    [/#if]
	                [/#if]
					[#list pager.beginIndex..pager.endIndex as i]
					[#if pager.endIndex <= pager.numPages]
	                     [#if i != pager.currentPage]
	                         <a href="${pager.getPageLink(i)}">${i}</a>
	                     [#else]
	                         <a class="page_current" href="${pager.getPageLink(i)}">${i}</a>
	                     [/#if]
	                 [/#if]
                	[/#list]
                	
                	[#if pager.endIndex < pager.numPages ]
	                	[#if pager.endIndex < pager.numPages - 1]
	                    	<span>...</span>
	                    [/#if]
	                    <a href="${pager.getPageLink(pager.numPages)}" title="页面">${pager.numPages}</a>
	                [/#if]

					[#if pager.currentPage < pager.numPages]
					<a href="${pager.getPageLink(pager.currentPage + 1)}">下一页<img src="resources/templating-kit/themes/pop/img/icons/nextPage.png" class="rightImg"></a>
					[/#if]
					&nbsp;共${pager.numPages}页
					&nbsp;&nbsp;&nbsp;去第
					<input type="text" class="number" id="inputNumPage" url="" value="1">页
					<input type="button" id="pageSubmit" class="pagebutton" value="跳转">
					<input type="hidden" id="hiddenNumPage" value="${pager.getPageLink(1)}">
				</div>
			</div>
        [/#if]
    [/#if]
[/#macro]

<script>
	$(function(){
		$('.pagebutton').click(function(){
			var Num = $('#inputNumPage').val().trim();
			if(Num ==''){
			}else{
			var Url = $('#hiddenNumPage').val();
			var begin = Url.lastIndexOf('currentPage=');
			var goUrl = Url.substring(0,begin+12)+Num;
			window.location.href= goUrl;
			}
		})
	});
</script>
