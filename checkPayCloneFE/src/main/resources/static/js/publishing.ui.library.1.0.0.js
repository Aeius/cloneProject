﻿(function ($){
    //tabs have 3 style normorl fade and animate
    $.fn.tabs = function(options){
        try{
            var defaults = {wrapClass:"x",clickClass:"x",showClass:"x",clickToDefaultTab:[],animate:false,fade:false,animateBorder:["false",".x"],ontentWidth:false,speed:300};
            options = $.extend(defaults,options);
            var classWrap = options.wrapClass;
            var clickClass = options.clickClass;
            var showClass = options.showClass;
            //alert(options.animateBorder[0]);
            if(options.animateBorder[0] === "true"){
                var attrDiv = "<span class="+classWrap.substring(1)+"_border_animate"+">"+"</span>";
                $(classWrap).each(function(i){
                    try{
                        if($(classWrap).eq(i).find(options.animateBorder[1].substring(1)).find(classWrap+"_border_animate").length == 0){
                            $(classWrap).eq(i).find(options.animateBorder[1]).append(attrDiv);
                            var borderWith = $(classWrap).eq(i).find(clickClass).eq(0).children().outerWidth();
                            var parentOffset = $(classWrap).eq(i).find(clickClass).eq(0).parent().offset();
                            var firstOffet = $(classWrap).eq(i).find(clickClass).eq(0).children().offset();
                            var totalOffet = firstOffet.left - parentOffset.left;
                            $(classWrap).eq(i).find(classWrap+"_border_animate").css({"position":"absolute","bottom":0,"height":5,"background-color":"green","z-index":"99999","width":borderWith,"margin-left":totalOffet,"transition":"width 0.7s"});
                        }
                    }catch(err){
                        //console.log(err);
                    }
                });
            }
            if(options.animate){
                var attrDiv = "<div class="+classWrap.substring(1)+"_contents"+">"+"</div>";
                $(classWrap +" "+showClass).parent().wrapInner(function() {
                    return attrDiv;
                });
                $(classWrap).find(showClass).css({"display":"block"});
                $(classWrap+" "+classWrap+"_contents").addClass("cboth");
                var contenlng,wrapWidth,contenWidth;
                $(classWrap).each(function(index) {
                    if(options.contentWidthFull){
                        $(classWrap).eq(index).find(showClass).css({"float":"left","width":"100%"});
                    }else{
                        $(classWrap).eq(index).find(showClass).css({"float":"left"});
                    }
                    contenlng = $(classWrap).eq(index).find(showClass).length;
                    contenWidth = $(classWrap).eq(index).find(showClass).outerWidth();
                    wrapWidth = contenlng*contenWidth;
                    $(classWrap+"_contents").eq(index).css({"width":wrapWidth});
                });
                var withcnt = wrapWidth/contenlng;
                if(options.contentWidthFull){
                    $(classWrap+" "+showClass).css({"width":withcnt});
                }
            }
            $(classWrap+" "+clickClass).click(function(e){
                var thisParentIndex = $(this).parents(classWrap).index("body "+classWrap);
                var thisIndex = $(this).index(classWrap+':eq('+thisParentIndex+')'+" "+clickClass);

//              //연동기관, 연동완료탭의 하위 탭들중 은행만 남기고 선택이 안되게 처리 20210413 mswoo
//              var isClick = false;
//              if(options.clickEnableIndex && classWrap == ".tabInter2"){
//                  var indexArray = options.clickEnableIndex.split(",");
//                  for(var i = 0; i < indexArray.length; i++){
//                      if(thisIndex+"" == indexArray[i]){
//                          isClick = true;
//                          break;
//                      }
//                  }
//              } else {
//                  isClick = true;
//              }
//              if(!isClick){
//                  return false;
//              }
                $(classWrap+':eq('+thisParentIndex+')'+" "+clickClass).removeClass("on");
                $(this).addClass("on");
                if(options.animateBorder){
                        var thisWidth =  $(classWrap).eq(thisParentIndex).find(clickClass).eq(thisIndex).children().outerWidth();
                        var parentOffset = $(classWrap).eq(thisParentIndex).find(clickClass).eq(thisIndex).parent().offset().left;
                        var bdmargin = $(classWrap).eq(thisParentIndex).find(clickClass).eq(thisIndex).children().offset().left-parentOffset;
                        $(classWrap).eq(thisParentIndex).find(classWrap+"_border_animate").css({"width":thisWidth});
                        $(classWrap).eq(thisParentIndex).find(classWrap+"_border_animate").animate({
                            marginLeft:bdmargin
                        },options.speed);
                   }
                if(options.fade){
                    $(classWrap).eq(thisParentIndex).find(showClass).css({"display":"none"});
                    $(classWrap).eq(thisParentIndex).find(showClass).eq(thisIndex).fadeIn(options.speed,"linear");
                }else if(options.animate){
                    var margin = $(classWrap).eq(thisParentIndex).find(showClass).eq(thisIndex).outerWidth()*thisIndex;
                    $(classWrap).eq(thisParentIndex).find(classWrap+"_contents").animate({
                        marginLeft:-margin
                    },options.speed);
                }else{
                    $(classWrap).eq(thisParentIndex).find(showClass).css("display","none");
                    $(classWrap).eq(thisParentIndex).find(showClass).eq(thisIndex).css("display","block");
                }

            });
        }catch(err){
                //console.log(err);
        }
    };
    //tabs

}( jQuery ));

// 2021-06-30
$(document).ready(function(){

	/* 팝업 접근성 초기값 */
	$('.layer_popup, .layer_bottomsheet').attr({'role':'dialog','tabindex':'-1','aria-hidden':'true'});
	/* 오픈상태 레이어팝업 접근성 */
    if($('.open_layer').length){
        setTimeout(function() {
            $('.open_layer').show().attr({'tabindex':'0','aria-hidden':'false'}).focus();
            //$('html').addClass('scroll_lock');
        }, 0);
    }
    /* 컨텐츠 여백 */
    $('.wrap_assets .wrap_slider.type_acc').closest('.wrap_assets').next('.btn_wrap').css('padding-top','25px');
    /* 이메일 전송받기 체크 박스라인 */
    $('.box_inp_ck input').each(function(){
        var $self = $(this);
        $self.on('change', function(){
            if($self.is(":checked") == true){
                $self.closest('.box_inp_ck').addClass('on');
            }else{
                $self.closest('.box_inp_ck').removeClass('on');
            }
        })
    })
	/* 차트 구분 */
    if($('.asGroup.type_chart').length){
        $('.asGroup.type_chart').closest('.wrap_assets').addClass('assets_chart');
    }
    /* 하단고정버튼 체크 */
    if($('.fix_bottom .btn_wrap').length){
        $('.container').addClass('has_fix_btn');
    }
    /* 핀테크 메인 bg */
    if($('.wrap.bg_lgray').length){
        $('.wrap.bg_lgray').closest('body').addClass('bg_lgray');
    }

    bsPageTypeHeight();         // 바텀시트 페이지타입 높이
	bottomFixActionBar();       // 하단 고정 바
    contentsBottomPadding();    // 컨텐츠영역 하단 패딩 = 하단 고정높이값
    prdRecommendBanner();       // 상품 추천 배너
    scrollTabShowArrWrap();     // 기관선택:XC1100(데이터연동:W1000) 탭 좌우 스크롤 화살표
    jsTabMenu();                // faq tab
    jsTxValReset();             // text val reset
    kepadOpenInpTxt();          // 앱 키패드 올라올때
    acHideOnlyOne();            // 메인 아코디언 1개일때 숨기기
    orgSelScroll();             // 2022-05 알고하는 동의 기관선택 스크롤
    layerFPageBottomPadding();  // XX1300 wrap_full_page 하단 여백조정
});

// [S] 메인 아코디언 1개일때 숨기기
function acHideOnlyOne(){
    if($('.assets.agChkItem').length > 1){
        $('.assets.agChkItem').removeClass('hide');
    }
}
// [E] 메인 아코디언 1개일때 숨기기

// [S] 앱 키패드 올라올때
function kepadOpenInpTxt(){
    $('input[type=text]').focusin(function() {
        var $fixBottom = $('.fix_bottom');
        $fixBottom.css({'position':'relative'}).find('.btn_wrap').addClass('fixBtnWrapPd').closest('.content').addClass('pdb0');//하단고정버튼 페이지 위치로 2022-08-25
       // $('html, body').animate({scrollTop: 200}, 300);
        $fixBottom.find('.dock_bar').hide();
    });
    $('input[type=text]').focusout(function() {
        var $fixBottom = $('.fix_bottom');
        setTimeout(function(){
            $fixBottom.css({'position':'fixed'}).find('.btn_wrap').removeClass('fixBtnWrapPd').closest('.content').removeClass('pdb0');//하단고정버튼 원래위치로 2022-08-25
            $fixBottom.find('.dock_bar').fadeIn();
        }, 100);
    });
}
// [E] 앱 키패드 올라올때

// [S] js text val reset
function jsTxValReset(){
    if($('.js_tx_val').length){
        $('.js_tx_val').each(function(){
            var textEle = $(this);
            var btnTxtValReset = textEle.siblings('.js_bt_val_reset');
            btnTxtValReset.hide();
            textEle.on('keyup', function(){
                // val값 초기화
                if(textEle.val().length === 0){
                    btnTxtValReset.hide();
                }else{
                    btnTxtValReset.show();
                }
            });
            btnTxtValReset.on('click', function(){
                btnTxtValReset.hide();
                setTimeout(function(){
                    textEle.val('').focus();
                }, 100);
            });
        });
    }
}
// [E] js text val reset

// [S] js tab Menu (faq)
function jsTabMenu(){
    if($('.js_tab_wrap').length){
        $('.js_tab_menu .js_bt').each(function(){
            var $self = $(this);
            var target = $($self.attr('data-target'));

            $self.on('click', function(){
                $self.closest('.js_item').siblings('.js_item').removeClass('on').find('.js_bt').removeAttr('title');
                $self.closest('.js_item').addClass('on').find('.js_bt').attr('title','선택됨');
                target.siblings('.js_tab_cont').removeClass('show');
                target.addClass('show');
            })
        });
    }
}
// [E] js tab Menu (faq)

// [S] 상품 추천 배너
function prdRecommendBanner(){
    $('.expanded_switch .inp_switch').each(function(){
        var $self = $(this);
        var bdBox = $self.closest('.expanded_switch').next('.bd_box');
        if($self.is(":checked") == true){
            $self.attr('aria-expanded','true');
            $self.closest('.expanded_switch').addClass('expanded');
            $self.closest('.expanded_switch').find('.tx_switch').addClass('pointColor');
        }else {
            $self.attr('aria-expanded','false');
            $self.closest('.expanded_switch').removeClass('expanded');
            $self.closest('.expanded_switch').find('.tx_switch').removeClass('pointColor');
        }
        $self.on('change', function(){
            if($self.is(":checked") == true){
                $(bdBox).slideDown(200);
                $self.attr('aria-expanded','true');
                $self.closest('.expanded_switch').addClass('expanded');
                $self.closest('.expanded_switch').find('.tx_switch').addClass('pointColor');
            }else{
                $self.attr('aria-expanded','false');
                $(bdBox).slideUp(200);
                $self.closest('.expanded_switch').removeClass('expanded');
                $self.closest('.expanded_switch').find('.tx_switch').removeClass('pointColor');
            }
        })
    })
}
// [E] 상품 추천 배너

// [S] 기관선택 탭 좌우 스크롤 화살표
function scrollTabShowArr(){
    var $scrollTabHeader = $('.tab_certi .tabs_header, .tabSetting .tabs_header');
    $tabsInnerWidth = '';
    $(this).each(function(){
        var liItems = $(this);
        var Sum = 0;
        if(liItems.children('li').length >= 1){
            $(this).children('li').each(function(i, e){
                Sum += $(e).outerWidth(true);
            });
        }
        $tabsInnerWidth = Math.ceil(Sum - 1);
    });
    if($(this).scrollLeft() > 0){
        $scrollTabHeader.removeClass('hide_l hide_r');
        if($(this).scrollLeft() >= $tabsInnerWidth - $(this).width()){
            $scrollTabHeader.addClass('hide_r');
        }
    }else{
        $scrollTabHeader.addClass('hide_l');
    }
}
function scrollTabShowArrWrap(){
    var $scrollTabHeader = $('.tab_certi .tabs_header, .tabSetting .tabs_header');
    var $scrollTabUl = $('.tab_certi .tabs_header ul, .tabSetting .tabs_header ul');
    var $scrollLiSum = 0;
    $scrollTabUl.children('li').each(function(i, e){
        $scrollLiSum += $(e).outerWidth(true);
    });
    // 스크롤 영역이 디바이스영역보다 작을때
    if($(document).width() >= $scrollLiSum - 5){
        $scrollTabHeader.addClass('hide_l hide_r');
    } else {
        scrollTabShowArr();
        $scrollTabUl.on('scroll',scrollTabShowArr);
        //on 메뉴 위치이동
        if($scrollTabUl.children('li.on').length){
            if($(document).width() - 50 < $scrollTabUl.children('li.on').offset().left + 50){
                var _scrollTabUlX = $scrollTabUl.scrollLeft();
                $scrollTabUl.scrollLeft(_scrollTabUlX + $scrollTabUl.children('li.on').offset().left - $(document).width()/2 + 25);
            }
        }
    }
}
// [E] 기관선택 탭 좌우 스크롤 화살표

// [S] 오픈상태레이어팝업
$(document).on("click", ".layer_popup.open_layer .js_pop_close, .layer_popup.type_event .js_pop_close, .layer_popup.type_user_guide .js_pop_close, .layer_popup.type_full_page .js_pop_close, .layer_popup.type_bs .js_pop_close, .js_only_pop_shown .js_pop_close", function(){
	$(this).closest('.layer_popup').hide().attr({'tabindex':'-1','aria-hidden':'true'});
    $('html').removeClass('scroll_lock');
});
// [E] 오픈상태레이어팝업

// [S] 레이어팝업
var $focusReturn;
var $focusReturn1;
var $focusReturn2;
var $focusReturn3;
$(document).on("click", ".btn_open_layer_pop", function(e){
    e.preventDefault();
	var $href = $(this).attr('href');
	layer_popup($href);
	$('html').addClass('scroll_lock');
	var dimIdx = $('.dim').index();
	eval('$focusReturn' + [dimIdx] + '= $(this)');
});
function layer_popup(el){
	var $el = $(el);
	$el.append("<div class=\"dim\"></div>");
	isDim ? $('.layer_popup').fadeIn() : $el.fadeIn().attr({'tabindex':'0','aria-hidden':'false'}).focus();

	//var isDim = $el.siblings().hasClass('dim');
	var isDim;
	var dimIdx = $('.dim').index();
	var thisDim = $el.find('.dim');
	var thisLyZindex = dimIdx + 4000;
	$('.dim').css('opacity','0');
	thisDim.css('opacity','.7');
	thisDim.closest('.layer_bottomsheet, .layer_popup').css('z-index', thisLyZindex);

	$el.find('.js_pop_close').click(function(){
		isDim ? $('.layer_popup').hide() : $el.hide();
		$('html').removeClass('scroll_lock');
		$el.attr({'tabindex':'-1','aria-hidden':'true'}).find(".dim").remove();
        if($('.dim').length == '2'){
            $('.dim:eq(1)').css('opacity','.7');
        }else if($('.dim').length == '1'){
            $('.dim').css('opacity','.7');
        }
		eval('$focusReturn'+ [dimIdx]).focus();	// multi layer
		return false;
	});
}
// [E] 레이어팝업

// [S] 바텀시트 페이지타입 높이
function bsPageTypeHeight(){
    if($('.layerBtmNew').length){
        var windowInnerHeight = $(window).innerHeight() - 120;
        $('.layer_bottomsheet').each(function () {
            var footerBtnHeight = $(this).find('.layer_footer .js_pop_close').height();
            //console.log(footerBtnHeight);
            if($(this).find('.layer_footer').length){
                $(this).find('.layer_cont').css('max-height',windowInnerHeight - footerBtnHeight);
            }else{
                $(this).find('.layer_cont').css('max-height',windowInnerHeight);
            }
        });
    }else{
        var windowInnerHeight = $(window).innerHeight();
        $('.layer_bottomsheet').each(function () {
            var footerBtnHeight = $(this).find('.layer_footer .js_pop_close').height();
            //console.log(footerBtnHeight);
            if($(this).find('.layer_inner .layer_tit').length){
                //console.log('tit 있음');
                var titHeight =
                parseInt($(this).find('.layer_tit').css('min-height'))
                //console.log(titHeight);
                if($(this).find('.layer_footer').length){
                    $(this).find('.layer_cont').css('max-height',windowInnerHeight - titHeight - footerBtnHeight - 30);
                }else{
                    $(this).find('.layer_cont').css('max-height',windowInnerHeight - titHeight - 30 );
                }
            }else{
                //console.log('tit 없음');
                if($(this).find('.layer_footer').length){
                    $(this).find('.layer_cont').css('max-height',windowInnerHeight - footerBtnHeight - 30);
                }else{
                    $(this).find('.layer_cont').css('max-height',windowInnerHeight - 30 );
                }
            }
        });
    }
}
// [E] 바텀시트 페이지타입 높이

// [S] 컨텐츠영역 하단 패딩 = 하단 고정높이값
function contentsBottomPadding(){
    if($('.fix_bottom').length){
		var fixBottomHeight = $('.fix_bottom').height();
		$('.content').css('padding-bottom',fixBottomHeight);
        if($('.headerType2').length){
            $('.content').css('padding-bottom',fixBottomHeight + 52);
        }
        if(!$('.fix_bottom .btn_wrap').length){
            $('.content').css('padding-bottom', fixBottomHeight + 32);
        }
        $('.btn_wrap + .fix_bottom').prev('.btn_wrap').css('padding-bottom','0');
        $('.btn_wrap + .toastMsg + .fix_bottom').prev().prev('.btn_wrap').css('padding-bottom','0');
        //캘린더
        if($('.tabs_wrap.tab_calendar')){
            $('.tabs_wrap.tab_calendar').closest('.content').css('padding-bottom', fixBottomHeight);
        }
        //docbar
        if(!$('.fix_bottom .dock_bar').length){
            $('.fix_bottom').addClass('not_dock');
        }
	}
    if($('.toastMsg').length){
        $('.btn_wrap + .toastMsg').prev('.btn_wrap').css('padding-bottom','0');
    }
}
// [E] 컨텐츠영역 하단 패딩 = 하단 고정높이값

// [S] 하단 고정 바
function bottomFixActionBar(){
	if($('.fix_bottom .dock_bar').length){
		var lastScrollTop = 0;
		var delta = 0;
		var fixBox = document.querySelector('.fix_bottom');
		var didScroll;
        $(fixBox).addClass('show');
		//스크롤 이벤트
		window.onscroll = function(e) {
			didScroll = true;
		};
		//0.25초마다 스크롤 여부 체크하여 스크롤 중이면 hasScrolled() 호출
		setInterval(function(){
			if(didScroll){
				hasScrolled();
				didScroll = false;
			}
		}, 250);
		function hasScrolled(){
			var nowScrollTop = window.scrollY;
            if(Math.abs(lastScrollTop - nowScrollTop) <= delta){
                return;
            }
            if(nowScrollTop + window.innerHeight >= document.body.offsetHeight - 10){
                //Scroll end
                fixBox.classList.add('show');
            }else if(nowScrollTop > lastScrollTop){// && nowScrollTop > fixBoxHeight
                //Scroll down
                fixBox.classList.remove('show');
            }else{
                if(nowScrollTop + window.innerHeight < document.body.offsetHeight - 10){
                    //Scroll up
                    fixBox.classList.add('show');
                }
            }
			lastScrollTop = nowScrollTop;
            //console.log('nowScrollTop: '+nowScrollTop, ' window.innerHeight: '+window.innerHeight, ' document.body.offsetHeight: '+document.body.offsetHeight, ' document.body.offsetHeight: '+document.body.offsetHeight);
		}
	}
}
// [E] 하단 고정 바

/* [S] 2022-05 알고하는 동의 기관선택 스크롤 */
function orgSelScroll(){

    if($('.tabs_wrap.type_scroll').length){
        var $topScSpace = $('.header').outerHeight(true) + $('.tit2_wrap').outerHeight(true) + parseInt($('.tab_certi').css('margin-top'));

        //console.log('$topScSpace: '+$topScSpace, ', .header / height: '+$('.header').outerHeight(true) ,', .tit2_wrap / height: '+ $('.tit2_wrap').outerHeight(true) ,', .tab_certi / margin-top: '+ parseInt($('.tab_certi').css('margin-top')));

        $('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap').each(function (index, item) {
            $(item).addClass('org_nth_' + (index + 1));
            var $orgNth = index + 1;

            $(document).on("click", ".tabs_wrap .btn_sel_org:nth-child("+ $orgNth +") a", function(){

                if($orgNth == 1){
                    $('html, body').animate({scrollTop: $topScSpace}, 300);
                }else if($orgNth == 2){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true)}, 300);
                }else if($orgNth == 3){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true) + $('.org_nth_2').outerHeight(true)}, 300);
                }else if($orgNth == 4){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true) + $('.org_nth_2').outerHeight(true) + $('.org_nth_3').outerHeight(true)}, 300);
                }else if($orgNth == 5){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true) + $('.org_nth_2').outerHeight(true) + $('.org_nth_3').outerHeight(true) + $('.org_nth_4').outerHeight(true)}, 300);
                }else if($orgNth == 6){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true) + $('.org_nth_2').outerHeight(true) + $('.org_nth_3').outerHeight(true) + $('.org_nth_4').outerHeight(true) + $('.org_nth_5').outerHeight(true)}, 300);
                }else if($orgNth == 7){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true) + $('.org_nth_2').outerHeight(true) + $('.org_nth_3').outerHeight(true) + $('.org_nth_4').outerHeight(true) + $('.org_nth_5').outerHeight(true) + $('.org_nth_6').outerHeight(true)}, 300);
                }else if($orgNth == 8){
                    $('html, body').animate({scrollTop: $topScSpace + $('.org_nth_1').outerHeight(true) + $('.org_nth_2').outerHeight(true) + $('.org_nth_3').outerHeight(true) + $('.org_nth_4').outerHeight(true) + $('.org_nth_5').outerHeight(true) + $('.org_nth_6').outerHeight(true) + $('.org_nth_7').outerHeight(true)}, 300);
                }
            });
        });

        $(window).scroll(function(){
            var $windowTop = $(window).scrollTop();
            var $activePosArea = $topScSpace - 60;
            //var $activePosArea = - parseInt($(window).height()/2.5) + $topScSpace;
            //console.log('$activePosArea:'+$activePosArea, '$windowTop:'+$windowTop, '$(window).height()/2.5 :'+parseInt($(window).height()/2.5), '$topScSpace:' +$topScSpace);
            //console.log($topScSpace - 60);

            var $orgEq0 = $('.ins1Link_wrap .org_wrap:eq(0)').outerHeight(true);
            var $orgEq1 = $('.ins1Link_wrap .org_wrap:eq(1)').outerHeight(true);
            var $orgEq2 = $('.ins1Link_wrap .org_wrap:eq(2)').outerHeight(true);
            var $orgEq3 = $('.ins1Link_wrap .org_wrap:eq(3)').outerHeight(true);
            var $orgEq4 = $('.ins1Link_wrap .org_wrap:eq(4)').outerHeight(true);
            var $orgEq5 = $('.ins1Link_wrap .org_wrap:eq(5)').outerHeight(true);
            var $orgEq6 = $('.ins1Link_wrap .org_wrap:eq(6)').outerHeight(true);
            var $orgEq7 = $('.ins1Link_wrap .org_wrap:eq(7)').outerHeight(true);
            var $orgEq8 = $('.ins1Link_wrap .org_wrap:eq(8)').outerHeight(true);

            if($windowTop < $activePosArea + $orgEq0){
                $(".tabs_wrap .btn_sel_org:nth-child(1)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(0);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1){
                $(".tabs_wrap .btn_sel_org:nth-child(2)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(0);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1 + $orgEq2){
                $(".tabs_wrap .btn_sel_org:nth-child(3)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(10);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1 + $orgEq2 + $orgEq3){
                $(".tabs_wrap .btn_sel_org:nth-child(4)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(85);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1 + $orgEq2 + $orgEq3 + $orgEq4){
                $(".tabs_wrap .btn_sel_org:nth-child(5)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(150);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1 + $orgEq2 + $orgEq3 + $orgEq4 + $orgEq5){
                $(".tabs_wrap .btn_sel_org:nth-child(6)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(220);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1 + $orgEq2 + $orgEq3 + $orgEq4 + $orgEq5 + $orgEq6){
                $(".tabs_wrap .btn_sel_org:nth-child(7)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(220);
            }else if($windowTop < $activePosArea + $orgEq0 + $orgEq1 + $orgEq2 + $orgEq3 + $orgEq4 + $orgEq5 + $orgEq6 + $orgEq7){
                $(".tabs_wrap .btn_sel_org:nth-child(8)").addClass('on').siblings().removeClass('on');
                $('.tabs_header.type2 ul').scrollLeft(220);
            }
        });

        if($('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap:last-child').outerHeight(true) < $(window).height() - $('.fix_bottom').outerHeight(true) - $('.tabs_header').outerHeight(true)){
            $('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap:last-child').css('padding-bottom', $(window).height() - $('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap:last-child').height() - $('.fix_bottom').height() - $('.tabs_header').outerHeight(true));

            //console.log('.org_last_height >= window_height', $('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap:last-child').outerHeight(true), $(window).height(), $(window).height() - $('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap:last-child').outerHeight(true) - $('.fix_bottom').outerHeight(true) - $('.tabs_header').outerHeight(true)+'(디바이스높이, 컨텐츠높이 비교)');
            //console.log($('.tabs_wrap.type_scroll .ins1Link_wrap .org_wrap:last-child').outerHeight(true), $('.fix_bottom').outerHeight(true), $('.fix_bottom').offsetHeight, $('.tabs_header').outerHeight(true));
        }
    }

}
/* [E] 2022-05 알고하는 동의 기관선택 스크롤 */

/* [S] XX1300 wrap_full_page 하단 여백조정 */
function layerFPageBottomPadding(){
	if($('.wrap_full_page').length){
        var layerFixBottomHeight = $('.layer_popup .fix_bottom').height();
        $('.wrap_full_page .page_inner').css('padding-bottom', layerFixBottomHeight);
        //console.log(layerFixBottomHeight);
    }
}
/* [E] XX1300 wrap_full_page 하단 여백조정 */