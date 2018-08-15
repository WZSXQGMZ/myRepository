package com.scushare.utils;

public class Page {
	//��ǰҳ��-�������û�����
		private int currentPageNo = 1;
		
		//������������
		private int totalCount = 0;
		
		//ҳ������
		private int pageSize = 0;
		
		//��ҳ��-totalCount/pageSize��+1��
		private int totalPageCount = 1;
		
		//��ҳ������������ʾ5ҳ��¼����ʼҳ��Ϊ1
		private int startPage = 1;
		//��ҳ������������ʾ5ҳ��¼�����ҳ��Ϊ5
		private int endPage = 5;
		
		

		public int getStartPage() {
			return startPage;
		}

		public void setStartPage() {
			if(this.totalPageCount<=5){
			this.startPage = 1;
			}else{
				this.startPage = this.currentPageNo-2;
				if(this.startPage<=0){
					this.startPage = 1;
				}
			}
		}

		public int getEndPage() {
			
			return endPage;
		}

		public void setEndPage() {
			if(this.totalPageCount<=5){
				this.endPage =this.totalPageCount;
			}else{
				
				this.endPage = this.currentPageNo+2;
				if(this.endPage <5){
					this.endPage = 5;
				}
				if(this.endPage>totalPageCount){
					this.endPage =this.totalPageCount;
				}
			}
			
		}

		public int getCurrentPageNo() {
			return currentPageNo;
		}

		public void setCurrentPageNo(int currentPageNo) {
			if(currentPageNo > 0){
				this.currentPageNo = currentPageNo;
			}
		}

		public int getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(int totalCount) {
			if(totalCount > 0){
				this.totalCount = totalCount;
				//������ҳ��
			}
		}
		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			if(pageSize > 0){
				this.pageSize = pageSize;
			}
		}

		public int getTotalPageCount() {
			return totalPageCount;
		}

		public void setTotalPageCount(int totalPageCount) {
			if( totalPageCount % this.pageSize == 0){
				this.totalPageCount = totalPageCount / this.pageSize;
			}else if(totalPageCount % this.pageSize > 0){
				this.totalPageCount = totalPageCount / this.pageSize + 1;
			}else{
				this.totalPageCount = 0;
			}
		}
		
//		public  void setTotalPageCountByRs(){
//			if(this.totalCount % this.pageSize == 0){
//				this.totalPageCount = this.totalCount / this.pageSize;
//			}else if(this.totalCount % this.pageSize > 0){
//				this.totalPageCount = this.totalCount / this.pageSize + 1;
//			}else{
//				this.totalPageCount = 0;
//			}
//		}
		
		
}