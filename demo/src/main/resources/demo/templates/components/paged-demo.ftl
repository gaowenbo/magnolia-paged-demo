[#include "/demo/templates/macro/pagination.ftl"]

<ul>
[#list  model.result as result]
<li>${result.name!}</li>
[/#list]
</ul>
[@pagination model "bottom" /]