$( document ).ready(function() {    

    pageSize = 1;
    pagesCount = $(".content").length;
    console.log(pagesCount);
    var currentPage = 1;
    
    /////////// PREPARE NAV ///////////////
    var nav = '';
    var totalPages = Math.ceil(pagesCount / pageSize);
    for (var s=0; s<totalPages; s++){
        nav += '<li class="page-item"><a class="page-link" href="#">'+(s+1)+'</a></li>';
    }
    console.log(nav);
    $(".pag_prev").after(nav);
    $(".page-item").first().addClass("active");
    //////////////////////////////////////

    showPage = function() {
        $(".content").hide().each(function(n) {
            if (n >= pageSize * (currentPage - 1) && n < pageSize * currentPage)
                $(this).show();
        });
    }
    showPage();


    $(".pagination li.page-item").click(function() {
        $(".pagination li").removeClass("active");
        $(this).addClass("active");
        currentPage = parseInt($(this).text());
        showPage();
    });

    $(".pagination li.pag_prev").click(function() {
        if($(this).next().is('.active')) return;
        $('.page-item.active').removeClass('active').prev().addClass('active');
        currentPage = currentPage > 1 ? (currentPage-1) : 1;
        showPage();
    });

    $(".pagination li.pag_next").click(function() {
        if($(this).prev().is('.active')) return;
        $('.page-item.active').removeClass('active').next().addClass('active');
        currentPage = currentPage < totalPages ? (currentPage+1) : totalPages;
        showPage();
    });
});