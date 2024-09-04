let b_nicCheck_click = false;
// "닉네임중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_emailcheck_click = false;
// "이메일중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_pwdcheck_click = false;
// "비밀번호중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_telcheck_click = false;
// "연락처중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

$(document).ready(function() {

    $("span.error").hide();

    $("span#nicCheck").click(function(){// 비밀번호 중복체크

        b_pwdcheck_click = true;

        $.ajax({
            url:"http://localhost:8080/gaji/myedit/nicDuplicateCheck",
            data:{"nic":$("input#memberNic").val()}, // data 속성은 http://localhost:8080/gaji/myedit/pwdDuplicateCheck 로 전송해야할 데이터를 말한다.
            type:"post", // type 을 생략하면 type : "get" 이 디폴트로 선언된다.

            async:true,  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
                         // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.

            dataType : "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/idDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다.
                               // 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다.
                               // 만약에 dataType:"json" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다.

            success:function(json){
                console.log(JSON.stringify(json));

                if(json.nicDuplicateCheck != "Optional.empty"){
                    // 입력한 nic 이 이미 데이터베이스에 저장되어 있다면
                    if (confirm("기존 별명을 유지하시겠습니까?")){    //확인
                        $("span#nicCheckResult").html("해당 별명은 사용가능합니다.").css({"color":"navy"});
                    }
                    else{   //취소
                        $("input#memberNic").val("");
                        b_pwdcheck_click = false;
                        return;
                    }

                }
                else{

                    const nic = $("input#memberNic").val().trim();

                    if( nic == ""){
                        $("span#nicCheckResult").html("별명 값이 존재하지 않습니다!!").css({"color":"red"});
                        b_pwdcheck_click = false;
                    }

                    else{
                        // 입력한 nic 이 이미 데이터베이스에 없다면
                        $("span#nicCheckResult").html("해당 별명은 사용가능합니다.").css({"color":"navy"});
                    }

                }


            },

            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }

        });


    });// end of $("span#pwdcheck").click(function()


    $("span#pwdcheck").click(function(){// 비밀번호 중복체크

        const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
        // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
        const prev_pwd = $("input#memberPwd").val();
        // 기존 비밀번호

        const bool = regExp_pwd.test($("input#memberPwd").val());

        if(!bool) {
            // 암호가 정규표현식에 위배된 경우
            $("input#memberPwd").val("");
            $("input#memberPwd").parent().find("span.error").show();
            $("span#pwdCheckResult").html("");
            return;
        }
        else {
            // 암호가 정규표현식에 맞는 경우
            $("input#memberPwd").parent().find("span.error").hide();
        }

        b_pwdcheck_click = true;

        $.ajax({
            url:"http://localhost:8080/gaji/myedit/pwdDuplicateCheck",
            data:{"pwd":$("input#memberPwd").val()}, // data 속성은 http://localhost:8080/gaji/myedit/pwdDuplicateCheck 로 전송해야할 데이터를 말한다.
            type:"post", // type 을 생략하면 type : "get" 이 디폴트로 선언된다.

            async:true,  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
                         // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.

            dataType : "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/idDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다.
                               // 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다.
                               // 만약에 dataType:"json" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다.

            success:function(json){
                console.log(JSON.stringify(json));

                if(json.pwdDuplicateCheck != "Optional.empty"){
                    // 입력한 pwd 가 이미 데이터베이스에 저장되어 있다면
                    if (confirm("기존 비밀번호를 유지하시겠습니까?")){    //확인
                        $("span#pwdCheckResult").html("해당 비밀번호는 사용가능합니다.").css({"color":"navy"});
                    }
                    else{   //취소
                        $("input#memberPwd").val("");
                        b_pwdcheck_click = false;
                        return;
                    }

                }
                else{

                    const pwd = $("input#memberPwd").val().trim();

                    if( pwd == ""){
                        $("span#pwdCheckResult").html("비밀번호 값이 존재하지 않습니다!!").css({"color":"red"});
                        b_pwdcheck_click = false;
                    }

                    else{
                        // 입력한 pwd 가 이미 데이터베이스에 없다면
                        $("span#pwdCheckResult").html("해당 비밀번호는 사용가능합니다.").css({"color":"navy"});
                    }

                }


            },

            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }

        });


    });// end of $("span#pwdcheck").click(function()

    $("span#telcheck").click(function(){// 연락처 중복체크


        const regExp_tel = new RegExp(/^010{1}[1-9][0-9]{3}\d{4}$/);
        // 010포함한문자
        const prev_tel = $("input#memberTel").val();
        // 기존 연락처

        const bool = regExp_tel.test($("input#memberTel").val());

        if(!bool) {
            // 연락처가 정규표현식에 위배된 경우
            $("input#memberTel").val("");
            $("input#memberTel").parent().find("span.error").show();
            $("span#telCheckResult").html("");
            return;
        }
        else {
            // 연락처가 정규표현식에 맞는 경우
            $("input#memberTel").parent().find("span.error").hide();
        }


        b_telcheck_click = true;

        $.ajax({
            url:"http://localhost:8080/gaji/myedit/telDuplicateCheck",
            data:{"tel":$("input#memberTel").val()}, // data 속성은 http://localhost:9090/MyMVC/member/idDuplicateCheck.up 로 전송해야할 데이터를 말한다.
            type:"post", // type 을 생략하면 type : "get" 이 디폴트로 선언된다.

            async:true,  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
                         // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.

            dataType : "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/idDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다.
                               // 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다.
                               // 만약에 dataType:"json" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다.

            success:function(json){
                console.log(JSON.stringify(json));

                if(json.telDuplicateCheck != "Optional.empty"){
                    // 입력한 tel이 이미 데이터베이스에 저장되어 있다면
                    if (confirm("기존 연락처를 유지하시겠습니까?")){    //확인
                        $("span#telCheckResult").html("해당 연락처는 사용가능합니다.").css({"color":"navy"});
                    }
                    else{   //취소
                        $("input#memberTel").val("");
                        b_telcheck_click = false;
                        return;
                    }

                }
                else{

                    const tel = $("input#memberTel").val().trim();

                    if(tel == ""){
                        $("span#telCheckResult").html("연락처 값이 존재하지 않습니다!!").css({"color":"red"});
                        b_telcheck_click = false;
                    }

                    else{
                        // 입력한 tel 가 이미 데이터베이스에 없다면
                        $("span#telCheckResult").html("해당 연락처는 사용가능합니다.").css({"color":"navy"});
                    }

                }


            },

            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }

        });


    });// end of $("span#telcheck").click(function()


    $("span#emailcheck").click(function(){// 이메일 중복체크


        const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
        // 이메일 정규표현식 객체 생성

        const prev_email = $("input#memberEmail").val();
        // 기존 이메일

        const bool = regExp_email.test($("input#memberEmail").val());

        if(!bool) {
            // 이메일 정규표현식에 위배된 경우
            $("input#memberEmail").val("");
            $("input#memberEmail").parent().find("span.error").show();
            $("span#emailCheckResult").html("");
            return;
        }
        else {
            // 연락처가 정규표현식에 맞는 경우
            $("input#memberEmail").parent().find("span.error").hide();
        }


        b_emailcheck_click = true;

        $.ajax({
            url:"http://localhost:8080/gaji/myedit/emailDuplicateCheck",
            data:{"email":$("input#memberEmail").val()}, // data 속성은 http://localhost:9090/MyMVC/member/idDuplicateCheck.up 로 전송해야할 데이터를 말한다.
            type:"post", // type 을 생략하면 type : "get" 이 디폴트로 선언된다.

            async:true,  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
                         // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.

            dataType : "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/idDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다.
                               // 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다.
                               // 만약에 dataType:"json" 으로 해주면 /MyMVC/member/idDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다.

            success:function(json){
                console.log(JSON.stringify(json));

                if(json.emailDuplicateCheck != "Optional.empty"){
                    // 입력한 email이 이미 데이터베이스에 저장되어 있다면
                    if (confirm("기존 이메일을 유지하시겠습니까?")){    //확인
                        $("span#emailCheckResult").html("해당 이메일은 사용가능합니다.").css({"color":"navy"});
                    }
                    else{   //취소
                        $("input#memberEmail").val("");
                        b_emailcheck_click = false;
                        return;
                    }

                }
                else{

                    const email = $("input#memberEmail").val().trim();

                    if(email == ""){
                        $("span#emailCheckResult").html("이메일 값이 존재하지 않습니다!!").css({"color":"red"});
                        b_emailcheck_click = false;
                    }

                    else{
                        // 입력한 email이 이미 데이터베이스에 없다면
                        $("span#emailCheckResult").html("해당 이메일은 사용가능합니다.").css({"color":"navy"});
                    }

                }


            },

            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }

        });

    });// end of $("span#telcheck").click(function()

    // ==>> 제품이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 시작 <<== //
    $(document).on("change", "input#formFile", function(e){

        const input_file = $(e.target).get(0);

        $("input#imgname").val(input_file.files[0].name);

        // console.log("확인용 이미지 이름", input_file.files[0].name);

        // 자바스크립트에서 file 객체의 실제 데이터(내용물)에 접근하기 위해 FileReader 객체를 생성하여 사용한다.
        const fileReader = new FileReader();

        fileReader.readAsDataURL(input_file.files[0]); // FileReader.readAsDataURL() --> 파일을 읽고, result 속성에 파일을 나타내는 URL을 저장 시켜준다.

        fileReader.onload = function(){ // FileReader.onload --> 파일 읽기 완료 성공시에만 작동하도록 하는 것임.

            document.getElementById("previewImg").src = fileReader.result; // ■■■■■■  id가 previewImg 이것인 img 태그에 위에서 얻어온 img.src값을 넣어준 것이다. ■■■■■■
        };

    }); // end of $(document).on("change", "input.img_file", function(e){}-------------------------------------------------------------------------------------------------------------
    // ==>> 제품이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 끝 <<== //



});

// Function Declaration

function goEdit() {

    // *** "비밀번호중복확인" 를 클릭했는지 검사하기 시작 *** //
    if(!b_pwdcheck_click) {
        // "비밀번호중복확인" 를 클릭 안 했을 경우
        alert("비밀번호중복확인를 클릭하셔서 비밀번호를 입력하셔야 합니다.");
        return; // goRegister() 함수를 종료한다.
    }
    // *** "비밀번호중복확인" 를 클릭했는지 검사하기 끝 *** //


    // *** "연락처중복확인" 를 클릭했는지 검사하기 시작 *** //
    if(!b_telcheck_click) {
        // "연락처중복확인" 를 클릭 안 했을 경우
        alert("연락처중복확인을 클릭하셔서 연락처를 입력하셔야 합니다.");
        return; // goRegister() 함수를 종료한다.
    }
    // *** "연락처중복확인" 를 클릭했는지 검사하기 끝 *** //



    // *** "이메일중복확인" 을 클릭했는지 검사하기 시작 *** //
    if( !b_emailcheck_click ) {
        // "이메일중복확인" 을 클릭 안 했을 경우
        alert("이메일 중복확인을 클릭하셔야 합니다.");
        return; // goRegister() 함수를 종료한다.
    }
    // *** "이메일중복확인" 을 클릭했는지 검사하기 끝 *** //



    goEdit_end();

} // end of function goEdit()---------------------


function goEdit_end(){// 첨부파일 없을 때 등록

    const frm = document.memberFrm;
    frm.method = "post";
    frm.action = "http://localhost:8080/gaji/myedit/editEnd";
    frm.submit();

}