package qici.model;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.RenderableDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import com.google.inject.Inject;


public class PagedModel<RD extends RenderableDefinition> extends RenderingModelImpl<RD>  {

	@Inject
	public PagedModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}


	protected Long count = null;
	protected Integer maxResultsPerPage = null;
    protected Integer currentPage = null;
    protected Integer numPages = null;
	List<Node> result = new ArrayList<Node>();
			
	//获取第i页的链接
	public String getPageLink(int i) {
	       return "?currentPage=" + i;
	    }
	    
	//获取起始页数
    public int getBeginIndex() {
        if (getCurrentPage() - 2 <= 1) {
            return 1;
        } else {
            return getCurrentPage() - 2;
        }
    }

    //获取结束页数
    public int getEndIndex() {
        if (getCurrentPage() + 2 >= getNumPages()) {
            return  getNumPages();
        } else {
            return getCurrentPage() + 2;
        }
    }
    
    //获取总个数
    public long getCount(){
    	if(count == null){
    		return 0;
    	}
    	return count;
    }
    
    //当前页数
    public int getCurrentPage(){
    	if(currentPage == null){
    		return 1;
    	}
    	return currentPage;
    }
    
    //每页最大数量
    public int getMaxResultsPerPage(){
    	if(maxResultsPerPage == null){
    		return 10;
    	}
    	return maxResultsPerPage;
    }
    
    //总页面数
    public int getNumPages() {
    	if(numPages == null){
    		return 1;
    	}
        return numPages;
    }
    
    //分页位置
    public String getPosition(){
    	return "both";
    }
    
    @Override
    public String execute() {
    	 try {
    		 String tmpCurrentPage = MgnlContext.getParameter("currentPage");
    	        if(!org.apache.commons.lang3.StringUtils.isEmpty(tmpCurrentPage) && org.apache.commons.lang3.StringUtils.isNumeric(tmpCurrentPage)){
    	        	if (Integer.parseInt(tmpCurrentPage) > 0){
    	                currentPage = Integer.parseInt(tmpCurrentPage);
    	        	}
    	        }
    	        if (currentPage == null) {
    	        	currentPage = 1;
    	        }
    	        final Session session = MgnlContext.getJCRSession("config");
    	        final QueryManager qm = session.getWorkspace().getQueryManager();
    	        String stmt = "select * from mgnl:content";
    	        Query q = qm.createQuery(stmt, Query.SQL);
    	        q.setLimit(10000);
    	     	QueryResult queryResult =  q.execute();
    	     	NodeIterator iterator = queryResult.getNodes();
    	     	count = iterator.getSize();
    	     	long offset = getMaxResultsPerPage() * (currentPage - 1);
    	     	if (offset + 1 > count) {
    	     		return "fail";
    	     	}
    	        iterator.skip(offset);
    	     	int i = 0;
    			while (iterator.hasNext() && i < getMaxResultsPerPage()) {
    				Node node = iterator.nextNode();
    				result.add(node);
    				i++;
    			}
                
                 numPages = (int) (count/getMaxResultsPerPage());
                 if((count % getMaxResultsPerPage()) > 0 ) {
                     numPages++;
                 }
                 
                 return "success";
         } catch (RepositoryException e) {
             throw new RuntimeException(e);
         }
    }
    
    //结果列表
    public Collection<Node> getResult() throws RepositoryException {
        return result;
    }
}
