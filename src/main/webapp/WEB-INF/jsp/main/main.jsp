<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VESTAP - HOME</title>
<link href="/resources/billboard/billboard.css" rel="stylesheet">
<link href="/resources/billboard/insight.css" rel="stylesheet">
<style>
button:focus {
	outline:none;
}
.round-button {
	font-size: 11pt;
	font-weight: bold;

}
.round-button.active {
	background-color: orange;
}

.estimation-intro {

	display:none;
	font-size: 16px;
}
.estimation-intro.active {

	display:table-cell;
}

.list-item {
	color: rgb(39, 49, 66);
}

.estimation-item > tbody > tr:hover{
	font-weight: 500;
	color: rgb(21, 150, 184);
}

.txt-left td{
text-align: left !important;
padding-left:30px !important;
padding-right:30px !important;
line-height:30px;
}

</style>
</head>
<body>
	<table class="offcanvas-table">
		<tr>
			<td class="col align-top p-5">
				<div class="row">
					<div class="col-3">
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-notice"> </i>부문
							</div>
						</div>
						<!-- //navbar -->
						<table class="table table-hover vestapTable">
							<tbody>
									<tr>
										<td>
											<button type="button" class="round-button active" onclick="fn_selectRisk('FC001');" id ="FC001"><img alt="" src="/resources/img/health.png" class="new-img" style="height:50%;"><span style="display:block;">건강</span></button>
											<button type="button" class="round-button" onclick="fn_selectRisk('FC002');" id="FC002"><img alt="" src="/resources/img/country.png" class="new-img" style="height:50%;"><span style="display:block;">국토/연안</span></button>
											<button type="button" class="round-button" onclick="fn_selectRisk('FC003');" id="FC003"><img alt="" src="/resources/img/land.png" class="new-img" style="height:50%;"><span style="display:block;">농축산</span></button>
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" class="round-button" onclick="fn_selectRisk('FC004');" id="FC004"><img alt="" src="/resources/img/forest.png" class="new-img" style="height:50%;"><span style="display:block;">산림/생태계</span></button>
											<button type="button" class="round-button" onclick="fn_selectRisk('FC005');" id="FC005"><img alt="" src="/resources/img/sea.png" class="new-img" style="height:50%;"><span style="display:block;">해양/수산</span></button>
											<button type="button" class="round-button" onclick="fn_selectRisk('FC006');" id="FC006"><img alt="" src="/resources/img/water.png" class="new-img" style="height:50%;"><span style="display:block;">물</span></button>
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" class="round-button" onclick="fn_selectRisk('FC007');" id="FC007"><img alt="" src="/resources/img/energy.png" class="new-img" style="height:50%;"><span style="display:block;">산업/에너지</span></button>
										</td>
									</tr>
							</tbody>
						</table>
					</div>
					<div class="col-5">
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-help"> </i>소개
							</div>
						</div>
						<!-- //navbar -->
						<div style="max-height: 350px;">
							<table class="table table-hover vestapTable" style="height:350px;">
								<thead>
									<tr>
										<th class="estimation-intro FC001 active" colspan="3"><h4>건강 부문의 취약성 평가 개요</h4></th>
									</tr>
									<tr style="display:none;">
										<th class="estimation-intro FC002" colspan="3"><h4>국토/연안 부문의 취약성 평가 개요</h4></th>
									</tr>
									<tr style="display:none;">
										<th class="estimation-intro FC003" colspan="3"><h4>농축산 부문의 취약성 평가 개요</h4></th>
									</tr>
									<tr style="display:none;">
										<th class="estimation-intro FC004" colspan="3"><h4>산림/생태계 부문의 취약성 평가 개요</h4></th>
									</tr>
									<tr style="display:none;">
										<th class="estimation-intro FC005" colspan="3"><h4>해양/수산 부문의 취약성 평가 개요</h4></th>
									</tr>
									<tr style="display:none;">
										<th class="estimation-intro FC006" colspan="3"><h4>물 부문의 취약성 평가 개요</h4></th>
									</tr>
									<tr style="display:none;">
										<th class="estimation-intro FC007" colspan="3"><h4>산업/에너지 부문의 취약성 평가 개요</h4></th>
									</tr>
								</thead>
								<tbody class="txt-left">
									<tr>
										<td class="estimation-intro FC001 active" colspan="3">
											기후변화는 열파나 극단적 기후와 대기오염 및 공기 알레르긴(Aeroallergens)의 영향과<br/> 같은 직접적 영향과 식량생산 및 공급, 매개체 및 설치류에 의한 전염병, 수인성감염질환, <br/>사회 경제적 혼란으로 인한 정신적 스트레스와 같은 간접적인 분야까지 <br/>건강과 광범위하게 영향을 미치므로 취약성평가를 통해 취약집단과 민감도를 파악하여 <br/>정책수립의 우선순위와 목표대상(target population)을 정할 수 있는 과학적 근거를 <br/>제시하는 연구와 근거에 기반을 둠
										</td>
										<td class="estimation-intro FC002" colspan="3">
											<span> 각 재해와 평가대상 기반시설 간의 인과관계를 검토하여 홍수, 폭염, 폭설, 해수면 상승 등</span><br/>
											<span> 재해에 의한 국토/연안을 평가 대상으로 함</span><br/>
											<span> 기후변화 적응을 고려한 친환경 국토이용계획 수립 및 국토관리체계 구축 도시의 기후변화 적응능력 제고<br> 해수면 상승 예측능력 제고 및 안전 대응체계 강화</span>
										</td>
										<td class="estimation-intro FC003" colspan="3">
											<span> 동일한 기후노출이라도 기후변화에 대한 적응의 유무에 따라서 작물 생산성에 차이가 나타남.</span><br/>
											<span> 적절한 적응이 존재하면 기후노출에 따른 생산량 손실이 개선되고, 수확량이 증가함(IPCC, 2007)</span><br/>
											<span> 기후변화에 대한 적응은 기후변화에 의해 야기된 많은 악영향을 상당히 감소시키고</span><br/>
											<span> 이로운 영향을 강화시킬 수 있는 가능성을 가짐</span><br/>
										</td>
										<td class="estimation-intro FC004" colspan="3">
											<span> 산림은 수원함양기능, 토사유출 방지기능, 대기 정화기능, 야생동물 보호기능, 산림 휴양서비스 제공 등</span><br/> 
											<span> 공익적 기능을 하는 간접적인 편익과 목재생산의 원재료를 공급하는 직접적인 편익 등</span><br/>
											<span> 다양한 기능을 제공함 또한, 산림은 이산화탄소를 흡수하는 저장원으로서 기후변화를 완화시키는 역할을 함.</span><br/>
											<span> 지구 전체 광합성량의 2/3을 차지하고 있으며 육상생태계 탄소의 80%, 토양탄소의 40%</span><br/>
											<span> 를 보유하고 있는 산림생태계가 기후변화로 영향을 받으면 대기와 교환하는 물, 에너지, 이산화탄소의 양도 달라지며 이로 인해 기후시스템에 영향을 미침</span><br/>
										</td>
										<td class="estimation-intro FC005" colspan="3">
											<span> 해수면 상승은 기후변화로 발생하는 가장 큰 위험요소로서 우리나라의 경우 1m 해수면 상승 시 70조 이상의 피해액 발생 예상</span><br/>
											<span> 이에 향후 예상되는 해수면 상승과 강화되는 연안 재해위험으로부터 국민의 재산과 영토를 방어할 체계적 관리 방안이 요구됨</span><br/>
											<span> 기후변화의 협상측면과 국가 미래를 위해 보다 신빙성 있는 과학적 정보의 생성과 보급이 필요함</span><br/>
											<span> 정확한 지형변동에 기반 한 해안선 보존 및 관리 방안을 통해 영토의 보존과 산업 시설 및 해상 수송의 안전을 유도하는 전략 마련 필요</span><br/>
										</td>
										<td class="estimation-intro FC006" colspan="3">
											<span> 기후 변화에는 수온상승과 함께 수체의 증발량, 유량 및 강우 유출량의 변화를 유발하여 수질 및 수생태계 건강에 직간접적인 큰 영향을 미침</span><br/>
											<span> 급변하는 이상 기후변화 현상과 관리가 취약한 요염원의 복합적 영향을 고려한다면 수질 및 수생태계 부문의 기후변화 영향 및 취약성 평가가 시급</span>
										</td>
										<td class="estimation-intro FC007" colspan="3">
											<span> 우리나라 환경에 적합한 산업/에너지 분야의 적응대책 수립을 위해서는 기후변화 영향 및 취약성평가가 필수</span><br/>
											<span> 에너지 및 산업 분야에 대한 구체적인 기후변화 영향 및 취약성 평가를 실시하여 효율적인 적응대책 수립 추진 필요</span><br/>
											<span> 신재생 에너지(풍력, 태양관) 사업, 폐기물, 에너지 작물을 잉요한 에너지화 사업 등 안정적인 에너지 공급기반 마련 필요</span><br/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="col-4">
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-download-disk"> </i>항목
							</div>
						</div>
						<div style="max-height:350px; overflow-y:auto;width:100%;">
							<table class="table table-hover vestapTable estimation-item">
								<thead>
									<tr>
										<th>항목명</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${itemList }" var="list">
										<tr>
											<td>
												<a class="list-item" href="#" onclick="fn_estimationGo('${list.itemId}');">${list.itemNm }</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="navbar p-0 pb-3">
					<div class="contentsTitle">
						<i class="icon-file-roll"> </i>최근 취약성평가 진행 내역
					</div>
				</div>
				<table class="table table-hover vestapTable smTable">
					<thead>
						<tr>
							<th>리스크</th>
							<th>평가 항목</th>
							<th>기후 모델</th>
							<th>시나리오</th>
							<th>연대</th>
							<th>행정구역</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty logList}">
						<c:forEach var="i" items="${logList }">
							<tr onclick="fn_moveEstimation('${i.id}')"><td>${i.fieldNm }</td><td>${i.itemNm}</td><td>${i.modelNm}</td><td>${i.sectionNm}</td><td>${i.yearNm }</td><td>${i.districtNm}</td></tr>
						</c:forEach>
						</c:if>
						<c:if test="${empty logList}">
							<tr><td colspan="6">취약성평가 진행 내역이 존재하지 않습니다.</td></tr>
						</c:if>
					</tbody>
				</table>
				<div class="row">
					<div class="col-4">
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-notice"> </i>공지사항
							</div>
						</div>
						<!-- //navbar -->
						<table class="table table-hover vestapTable">
							<thead>
								<tr>
									<th>번호</th>
									<th style="width: 350px;">제목</th>
									<th>작성일</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(noticeList) eq 0 }">
									<tr>
										<td colspan="5">등록 된 공지사항이 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach items="${noticeList }" var="list">
									<tr class="offcanvas-select"
										onclick="location.href='/member/base/board/notice/list.do?${_csrf.parameterName}=${_csrf.token}&mainIdx=${list.bbs_idx}'">
										<td><c:out value="${list.RNUM }" /></td>
										<td class="t-overflow"><c:out value="${list.bbs_title }" /></td>
										<td><c:out value="${list.bbs_regdate }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-4">
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-help"> </i>FAQ
							</div>
						</div>
						<!-- //navbar -->
						<table class="table table-hover vestapTable">
							<thead>
								<tr>
									<th>번호</th>
									<th style="width: 350px;">제목</th>
									<th>등록일</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(faqList) eq 0 }">
									<tr>
										<td colspan="5">등록 된 FAQ가 없습니다.</td>
									</tr>
								</c:if>

								<c:forEach items="${faqList }" var="list">
									<tr class="offcanvas-select"
										onclick="location.href='/member/base/board/faq/list.do?${_csrf.parameterName}=${_csrf.token}&mainIdx=${list.bbs_idx}'">
										<td><c:out value="${list.RNUM }" /></td>
										<td class="t-overflow"><c:out value="${list.bbs_title }" /></td>
										<td><c:out value="${list.bbs_regdate }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-4">
						<div class="navbar p-0 pb-3">
							<div class="contentsTitle">
								<i class="icon-download-disk"> </i>자료실
							</div>
						</div>
						<!-- //navbar -->
						<table class="table table-hover vestapTable">
							<thead>
								<tr>
									<th>번호</th>
									<th style="width: 350px;">제목</th>
									<th>등록일</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(referenceList) eq 0 }">
									<tr>
										<td colspan="5">등록 된 자료가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach items="${referenceList }" var="list">
									<tr class="offcanvas-select"
										onclick="location.href='/member/base/board/reference/list.do?${_csrf.parameterName}=${_csrf.token}&mainIdx=${list.bbs_idx}';">
										<td><c:out value="${list.RNUM }" /></td>
										<td class="t-overflow"><c:out value="${list.bbs_title }" /></td>
										<td><c:out value="${list.bbs_regdate }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</td>
		</tr>
	</table>
	<script>
	function fn_moveEstimation(key){
		var url = "/member/base/moveEstimation.do";
		var dataSet = {"key":key};
		$.ajax({
			url:url
			,data:dataSet
			,type:"get"
			,dataType:"json"
			,success:function(){
				location.href='/member/base/climate/estimation/view.do?${_csrf.parameterName}=${_csrf.token}&exe=do';
			}
		});
	}
	
	function fn_selectRisk(key) {
		$('.estimation-intro').parent().css("display","none");
		$('.round-button').removeClass('active');
		$('.estimation-intro').removeClass('active');
		$('.estimation-item').removeClass('active');
		
		$('#'+key).addClass('active');
		$('.estimation-intro.'+key).parent().css("display","table-row");
		$('.estimation-intro.'+key).addClass('active');
		$('.estimation-item.'+key).addClass('active');
		
		var url = "/member/base/selectRisk.do";
		var dataSet = {"field": key };
		
		$.ajax({
			url: url,
			data: dataSet,
			type:"get",
			dataType:"json",
			success:function(data){
				var list = data.itemList;
				var html = '';
				var j = 0;
				var k = 3;
				$(".estimation-item > tbody").empty();
				 
				for(var i in list){
					
					html += '<tr>';
					html += '<td><a class="list-item" href="#" onclick="fn_estimationGo(\''+ list[i].itemId+'\');">'+list[i].itemNm+'</td>';
					html += '</tr>';
				}
				
				$(".estimation-item > tbody").append(html);
			}
		});
		
	}
	
	function fn_estimationGo(key) {
		
		var url = "/member/base/moveEstimationNew.do";
		var dataSet = {"key":key};
		$.ajax({
			url:url
			,data:dataSet
			,type:"get"
			,dataType:"json"
			,success:function(){
				location.href='/member/base/climate/estimation/view.do?${_csrf.parameterName}=${_csrf.token}&exe=do';
			}
		});
	}
	</script>
	<script src="/resources/billboard/d3.v5.min.js"></script>
<script src="/resources/billboard/billboard.js"></script>
</body>
</html>