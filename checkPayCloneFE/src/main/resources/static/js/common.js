

// 쿠키 설정
function setCookie(cookieName, value, extimes) {
	var exdate = new Date();
	exdate.setDate(exdate.setDate + extimes);
	var cookieValue = escape(value)
		+ ((extimes == null) ? "" : "; expires=" + exdate.toGMTString());
	document.cookie = cookieName + "=" + cookieValue + "; path=/;";
}

// 쿠키 가져오기
function getCookie(cookieName) {
	cookieName = cookieName + '=';
	var cookieData = document.cookie;
	var start = cookieData.indexOf(cookieName);
	var cookieValue = '';
	if (start != -1) { // 쿠키가 존재하면
		start += cookieName.length;
		var end = cookieData.indexOf(';', start);
		if (end == -1) // 쿠키 값의 마지막 위치 인덱스 번호 설정 
			end = cookieData.length;
		cookieValue = cookieData.substring(start, end);
	} else {
		//쿠키가 존재하지 않음
		return 0;
	}
	// 쿠키가 존재하지 않을 떄 로그인 페이지 이외에서 접근
	if (cookieName == "jwt" + '=' && start == -1) {
		return location.replace('/login');
	}

	return unescape(cookieValue);
}

// 쿠키 삭제
function deleteCookie(cookieName) {
	var expireDate = new Date();
	expireDate.setDate(0);
	document.cookie = cookieName + "= " + "; expires="
		+ expireDate.toGMTString() + "; path=/";
}

async function tokenCall2(isReload = true) {
	//var baseUrl = "http://localhost:8081"
	var baseUrl = "http://192.168.240.208:8081"
	var rtk = getCookie("rtk");
	var id = parseJwt(getCookie("jwt")).sub;
	var role = parseJwt(getCookie("jwt")).role;

	return await $.ajax({
		url: baseUrl + "/api/login/refresh",
		type: 'post',
		data: {
			"id": id,
			"rtk": rtk
		},
		datatype: "text",
	}).done(function(data) {
		console.log(data);
		if (data != null && data.length > 2) {
			setCookie("jwt", data, 1);
			if (isReload) {
				history.go(0);
			}
		}
		else {
			if (role == "ADMIN") {
				deleteCookie('jwt')
				deleteCookie('rtk')
				window.location.href = "/admin/login"
			}
			else {
				deleteCookie('jwt')
				deleteCookie('rtk')
				window.location.href = "/login"
			}
		}
	})

}

// JWT decode
const parseJwt = (token) => {
	if (token == null || token == "") {
		return ;
	}
	var base64Url = token.split('.')[1];
	var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
	var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
		return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	}).join(''));

	return JSON.parse(jsonPayload);
};

// 천단위 콤마 함수
function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// [S] 코드 치환 함수
// 상환 방식 코드
function repay_method(code) {
	switch (code) {
		case "01": code = "만기일시상환"; break;
		case "02": code = "원금균등분할상환"; break;
		case "03": code = "거치식-원금균등분할상환"; break;
		case "04": code = "원리금균등분할상환"; break;
		case "05": code = "거치식-원리금균등분할상환"; break;
		case "06": code = "만기지정상환-원금균등분할상환"; break;
		case "07": code = "만기지정상환-원리금균등분할상환"; break;
		case "08": code = "한도거래"; break;
		case "09": code = "기타(직접산정)"; break;
		case "10": code = "거치식 만기지정상환-원금균등분할상환"; break;
		case "11": code = "거치식 만기지정상환-원리금균등분할상환"; break;
		case "12": code = "혼합상환방식"; break;
	}
	return code;
}
// 지출 카테고리 코드
function category_code(category_name) {
	switch (category_name) {
		case "식비": category_name = "1000"; break;
		case "카페/디저트": category_name = "1100"; break;
		case "술/유흥": category_name = "1200"; break;
		case "생활": category_name = "2000"; break;
		case "온라인쇼핑": category_name = "3000"; break;
		case "패션/쇼핑": category_name = "3100"; break;
		case "뷰티": category_name = "3200"; break;
		case "교통": category_name = "4000"; break;
		case "자동차": category_name = "4100"; break;
		case "주거/통신": category_name = "5000"; break;
		case "금융": category_name = "5100"; break;
		case "경조/선물": category_name = "5200"; break;
		case "의료/건강": category_name = "6000"; break;
		case "문화/여가": category_name = "7000"; break;
		case "여행/숙박": category_name = "7100"; break;
		case "교육/학습": category_name = "8000"; break;
		case "자녀/육아": category_name = "8100"; break;
		case "애완동물": category_name = "8200"; break;
		case "기타": category_name = "8300"; break;
	}
	return category_name;
}
// 입출금 관련 코드
function trans_type(type) {
	switch (type) {
		case '01': type = '신규'; break;
		case '02': type = '출금'; break;
		case '03': type = '입금'; break;
		case '04': type = '정정(입금)'; break;
		case '05': type = '정정(출금)'; break;
		case '06': type = '출금취소(입금)'; break;
		case '07': type = '입금취소(출금)'; break;
		case '98': type = '(기타)입금'; break;
		case '99': type = '(기타)출금'; break;
	}
	return type
}
// 카드 승인 상태 코드
function card_state(type) {
	switch (type) {
		case '01': type = '승인'; break;
		case '02': type = '승인취소'; break;
		case '03': type = '정정'; break;
	}
	return type;
}

// 계좌 구분 코드
function account_type(code) {
	switch (code) {
		case '101': code = '종합매매'; break;
		case '102': code = '위탁'; break;
		case '103': code = '파생상품'; break;
		case '104': code = '단기금융상품'; break;
		case '105': code = '연금'; break;
		case '106': code = '현물'; break;
		case '107': code = '퇴직연금'; break;
		case '108': code = '기타'; break;
	}
	return code;
}
// 상품 구분 코드
function invest_products_type(code) {
	switch (code) {
		case '401': code = '국내주식'; break;
		case '402': code = '해외주식'; break;
		case '403': code = '국내채권'; break;
		case '404': code = '해외채권'; break;
		case '405': code = '대출채권'; break;
		case '406': code = '국내파생결합증권'; break;
		case '407': code = '해외파생결합증권'; break;
		case '408': code = '국내장내파생상품'; break;
		case '409': code = '해외장내파생상품'; break;
		case '410': code = '기타파생상품'; break;
		case '411': code = '국내단기금융상품'; break;
		case '412': code = '해외단기금융상품'; break;
		case '413': code = '국내펀드'; break;
		case '414': code = '해외(역내)펀드'; break;
		case '415': code = '해외(역외)펀드'; break;
		case '416': code = '랩'; break;
		case '417': code = '신탁'; break;
		case '418': code = '현금과 예금'; break;
		case '419': code = '퇴직연금'; break;
		case '420': code = '현물'; break;
		case '421': code = '기타'; break;
	}
	return code;
}
// 거래 종류 코드
function invest_trans_code(code) {
	switch (code) {
		case '301': code = '입금'; break;
		case '302': code = '출금'; break;
		case '303': code = '상환'; break;
		case '311': code = '입금취소'; break;
		case '312': code = '출금취소'; break;
		case '313': code = '상환취소'; break;
		case '321': code = '입고'; break;
		case '322': code = '출고'; break;
		case '323': code = '입고취소'; break;
		case '324': code = '출고취소'; break;
		case '341': code = '매수'; break;
		case '342': code = '매도'; break;
		case '343': code = '매수취소'; break;
		case '344': code = '매도취소'; break;
		case '381': code = '기타(입)'; break;
		case '382': code = '기타(출)'; break;
		case '391': code = '기타취소(입)'; break;
		case '392': code = '기타취소(출)'; break;
	}
	return code;
}
// IRP 거래 종류 코드
function invest_irp_trans_code(code) {
	switch (code) {
		case '01' : code = '입금'; break;
		case '02' : code = '지급'; break;
	}
	return code;
}
// [E] 코드 치환 함수

// [S] 날짜 관련 함수
function year_month_date_dot(date) { // 포맷 yyyy.MM.dd
	var result = date.getFullYear() + '.'
	result += (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) + '.' : (date.getMonth() + 1) + '.'
	result += date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	return result;
}
function year_month_date_none(date) { // 포맷 yyyyMMdd
	var result = date.getFullYear()
	result += (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1).toString()
	result += date.getDate() < 10 ? "0" + date.getDate() : date.getDate().toString()
	return result;
}
function year_month_dot(date) { // 포맷 yyyy.MM
	var result = date.getFullYear() + '.'
	result += (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)
	return result;
}
function month_date_dot(date) { // 포맷 MM.dd
	var result = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) + '.' : (date.getMonth() + 1) + '.'
	result += date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	return result;
}
function month_date_none(date) { // 포맷 MMdd
	var result = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1): (date.getMonth() + 1)
	result += date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	return result;
}
function hours_minute_col(date) { // 포맷 HH:mm
	var result = (date.getHours()) < 10 ? "0" + (date.getHours()) + ':' : (date.getHours()) + ':'
	result += date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	return result;
}
function year_month_date_hours_min(date) { // 포맷 yyyy.MM.dd HH:mm
	var result = date.getFullYear() + '.'
	result += (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) + '.' : (date.getMonth() + 1) + '.'
	result += date.getDate() < 10 ? "0" + date.getDate() + " " : date.getDate() + " "
	result += date.getHours() < 10 ? "0" + date.getHours() + ":" : date.getHours() + ":"
	result += date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	return result;
}
function year_month_date_dash(date) { // 포맷 yyyy-MM-dd
	var result = date.getFullYear() + '-'
	result += (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) + '-' : (date.getMonth() + 1) + '-'
	result += date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	return result;
}
function toKor_dayOfWeek(dayOfWeek) { // 한글 요일
	switch (dayOfWeek) {
		case 0: dayOfWeek = '일요일'; break;
		case 1: dayOfWeek = '월요일'; break;
		case 2: dayOfWeek = '화요일'; break;
		case 3: dayOfWeek = '수요일'; break;
		case 4: dayOfWeek = '목요일'; break;
		case 5: dayOfWeek = '금요일'; break;
		case 6: dayOfWeek = '토요일'; break;
	}
	return dayOfWeek;
}
// [E] 날짜 관련 함수

// [S] 디테일 바로가기 04.19 태영, map으로 수정 04.28 정훈 
function detailmove(url,...args){		// key1, value1,key2,value2...
	let form=document.createElement('form');
	for(let i=0;i<args.length;i=i+2){
		let obj;
		obj=document.createElement('input');
		obj.setAttribute('type','hidden');
		obj.setAttribute('name',args[i]);
		obj.setAttribute('value',args[i+1]);
		form.appendChild(obj);
	}
	form.setAttribute('method', 'post');
	form.setAttribute('action', url);
	document.body.appendChild(form);
	form.submit();
}
// [E] 디테일 바로가기 04.19 태영, map으로 수정 04.28 정훈 

// [S] 유저 어드민 구분 / role에 맞는 로그인 페이지로 이동
function moveAlongRole(){
	if(window.location.pathname.endsWith('login')) return;
	if(getCookie('jwt')==0) return location.replace('/login');
	if(!window.location.pathname.startsWith("/admin")){
		if(parseJwt(getCookie('jwt')).role=='ADMIN'){
			alert('비정상적인 접근입니다.')
			return location.replace('/admin/login');
		}
	}else{
		if(parseJwt(getCookie('jwt')).role=='USER'){
			alert('비정상적인 접근입니다.')
			return location.replace('/login');
		}
	}
}
moveAlongRole()
// [E] 유저 어드민 구분 / role에 맞는 로그인 페이지로 이동




async function userErrorHandler(jqXHR){
	if (jqXHR.status == 401 && jqXHR.responseText == "토큰 기한 만료") {
		tokenCall2();
	}
	else{
		return location.replace('/login');
	}
}

async function adminErrorHandler(jqXHR){
	if (jqXHR.status == 401 && jqXHR.responseText == "토큰 기한 만료") {
		tokenCall2();
	}
	else{
		alert("권한 없음");
		return location.replace('/admin');
	}
}