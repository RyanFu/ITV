package com.itv.spider.s360.movie;

import java.util.List;
import com.itv.spider.AbstractSpider;

/**
 * 爬取电影列表页面
 * 
 * @author xiajun
 * 
 */
public class MoviePageSpider extends AbstractSpider {
	private String url;

	public MoviePageSpider(String url) {
		this.url = url;
	}

	public void run() {
		int i = url.lastIndexOf('/');
		i = url.substring(0, i).lastIndexOf('/');
		String downPage = null;
		String page_info = getPageInfo(this.url);
		do {
			List<String> list = MovieRegex.getMovieListUrl(page_info, url.substring(0, i));
			if (list != null) {
				for (String mv_url : list) {
					spiderPool.execute(new MovieInfoSpider(mv_url));
				}
			}
			downPage = MovieRegex.getMoviePageDownUrl(page_info);
			if (downPage != null) {
				page_info = getPageInfo(downPage);
			}
		} while (downPage != null);
	}

	public static void main(String[] args) {
		MoviePageSpider mp = new MoviePageSpider("http://v.360.cn/dianying/list.php?cat=103");
		mp.run();
	}
}
