let b_emailcheck_click = false;
// "이메일중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_pwdcheck_click = false;
// "비밀번호중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_telcheck_click = false;
// "연락처중복확인" 를 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

$(document).ready(function() {

    $("span.error").hide();

    $("span#pwdcheck").click(function(){// 비밀번호 중복체크

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

                if(json.n != 0){
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



});