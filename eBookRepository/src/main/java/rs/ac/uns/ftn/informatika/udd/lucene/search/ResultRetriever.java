package rs.ac.uns.ftn.informatika.udd.lucene.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.DocumentHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.TextDocHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.Word2007Handler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.WordHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.model.IndexUnit;
import rs.ac.uns.ftn.informatika.udd.lucene.model.RequiredHighlight;
import rs.ac.uns.ftn.informatika.udd.lucene.model.ResultData;

@Service
public class ResultRetriever {
	
	private ElasticsearchTemplate template;
	
	@Autowired
	public ResultRetriever(ElasticsearchTemplate template){
		this.template = template;
	}

	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();
		
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(query)
				.build();
       
        List<IndexUnit> indexUnits = template.queryForList(searchQuery, IndexUnit.class);

        for (IndexUnit indexUnit : indexUnits) {
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(), ""));
		}
        
		
		return results;
	}
	
	protected DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".txt")){
			return new TextDocHandler();
		}else if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}else if(fileName.endsWith(".doc")){
			return new WordHandler();
		}else if(fileName.endsWith(".docx")){
			return new Word2007Handler();
		}else{
			return null;
		}
	}
}
