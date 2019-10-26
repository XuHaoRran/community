function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,0,content);
}
;
/*
展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments =$("#comment-"+id);
    //获取二级展开状态
    var collapse = e.getAttribute("data-collapse");
    //zhedie
    if (collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
        e.setAttribute('style', 'color: grey');
    }else{
        $.getJSON("/api/comments/"+id,function(data){
           console.log(data);
        });
        //展开二级评论
        comments.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
        e.setAttribute('style', 'color: blue');
    }



}
function commentList() {

};
function comment2target(targetId, type,content) {
    $.ajax({
        contentType: 'application/json',

        type: "POST",
        dataType: "json",
        url: "/api/comment",

        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type

        }),
        success: function (response) {
            if (response.code == 200) {
                //采用异步增加评论数暂不要使用。。。。。。
                //这里使用了数据库的评论数+1,显示评论数
                $("#comment_section").hide();
                $("#comment_count").val(response.commentcount);
                console.log(response);
                return;
            }
            if (response.code==2003){
                var isAccepted = confirm(response.msg)
                if(isAccepted){
                    window.open("https://github.com/login/oauth/authorize?client_id=39cce7d9a15359c7f78a&redirect_url=http://localhost:8100/callback&scope=user&state=1")
                }
            }
            else if (response!= ''&& response.code!=''){
                alert(response.code+"   "+response.msg);
            }

            else {
                console.log("这里报错")
            }
        },
        error: function (response) {
            console.log("asdfasdfasdfa");
        },
    })
}
//二级评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,1,content);
}

function selectTag(value) {
    var previous = $("#tag").val();
    if (previous.indexOf(value)==-1){
        $("#tag").val(previous+','+value);
    }else{
        $("#tag").val(value);
    }
}