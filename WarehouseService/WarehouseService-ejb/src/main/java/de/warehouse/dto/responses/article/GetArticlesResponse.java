package de.warehouse.dto.responses.article;

import javax.xml.bind.annotation.XmlType;

import de.warehouse.dto.ArticleTO;
import de.warehouse.dto.ResponseBase;

/**
 * @author Florian
 */
@XmlType(name="GetArticlesResponse")
public class GetArticlesResponse extends ResponseBase {

	private static final long serialVersionUID = 2894439904171413991L;

	private ArticleTO[] articles;

	/**
	 * @return the articles
	 */
	public ArticleTO[] getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(ArticleTO[] articles) {
		this.articles = articles;
	}
}
