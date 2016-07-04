# magnolia-paged-demo
一个magnolia 的分页例子


tomcat 启动后 
在后台 resource模块中更改 /mtk/templates/pages/basic.yaml 资源，在尾部添加
      paged-demo:
        id: demo:components/paged-demo
        
添加一个页面，选择模板basic template
建好页面以后进入编辑，在main区域添加 paged-demo组件，既可看到效果
