// virspit api 연결 테스트코드입니다.

const LISTING_END_POINT = "http://3.35.78.136:8081/"; // listing server
const ORDER_END_POINT = "http://3.35.71.218:8081/"; // order server
const USER_END_POINT = "http://3.38.44.130:8081/"; // user server
const AUTH_END_POINT = "http://3.38.44.130:8083/"; // auth server
const PRODUCT_END_POINT = "http://15.165.34.36:8081/"; // product server

const headers = {
   accept: "application/json",
   "Content-Type": "application/json",
   "Access-Control-Allow-Origin": "*",
};

const writeInDocument = (result) => {
   console.log(result);
   document.querySelector("#app").innerText = result.message;
};

const request = async (url, config) => {
   try {
      const data = await fetch(url, config);
      if (data.ok) {
         const result = await data.json();
         writeInDocument(result);
      }
   } catch (error) {
      console.log(error);
      if (error.response) {
         console.log("error: ", error.response);
         writeInDocument(error.response.data);
      }
   }
};

const setQuery = (params) => {
   return Object.keys(params)
      .map((key) => encodeURIComponent(key) + "=" + encodeURIComponent(params[key]))
      .join("&");
};

/****************************
        여기부터 보세요
****************************/
// 브라우저 개발자도구의 콘솔창을 켜고 확인해주세요.
// 성공시 화면에 success 메세지 표시됩니다.
// 실패/에러발생 시 콘솔창 에러메세지 확인해주세요.


/****************************
 auth-server
 ****************************/

// GET /favorite/{id}
function getAuthFeignTest() {
    request(AUTH_END_POINT + "auth/check", { headers });
}

/****************************
        user-server
****************************/

// GET /favorite/{id}
function getMemberFavoriteProducts(id) {
   request(USER_END_POINT + "favorite/" + id, { headers });
}
// getMemberFavoriteProducts(1); // 해당 Id의 member 좋아요 누른 상품 목록 가져오기

// GET /member/{id}
function getMemberInformation(id) {
   request(USER_END_POINT + "member/" + id, { headers });
}
// getMemberInformation(1); // 멤버아이디로 멤버정보조회

// POST /member/save
function signup(obj) {
   request(USER_END_POINT + "member/save", { headers, method: "POST", body: JSON.stringify(obj) });
}
// signup({
//    birthdayDate: "2010-01-01",
//    email: "test@test.com",
//    gender: "MALE",
//    memberName: "김테스트",
//    password: "1234",
// }); // feign - 회원가입 요청한 Member를 db에 저장

// GET /wallet/{id}
function getWalletInformation(id) {
   request(USER_END_POINT + "wallet/" + id, { headers });
}
// getWalletInformation(1); // memberId 로 지갑 주소 찾기

/****************************
        product-server
****************************/

// GET /product
function getAllProducts() {
   request(PRODUCT_END_POINT + "product", { headers });
}
// getAllSports(1, 1); // 전체종목조회

// GET /sports
function getAllSports(page, size) {
   request(PRODUCT_END_POINT + "sports?" + setQuery({ page, size }), { headers });
}
// getAllProducts(); // 전체상품조회
