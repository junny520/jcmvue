webpackJsonp([1],{0:function(t,e){},"4yMX":function(t,e){},EzCN:function(t,e){},LTIx:function(t,e){},Lwfw:function(t,e){},NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});n("Xrl+"),n("fLmE");var i=n("7+uW"),o=n("NYxO"),s=n("EOUi"),a=n.n(s),r=n("8+8L"),l={modules:{navigator:{strict:!0,namespaced:!0,state:{stack:[],options:{}},mutations:{push:function(t,e){t.stack.push(e)},pop:function(t){t.stack.length>1&&t.stack.pop()},replace:function(t,e){t.stack.pop(),t.stack.push(e)},reset:function(t,e){t.stack=[e||t.stack[0]]},options:function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};t.options=e}}},splitter:{strict:!0,namespaced:!0,state:{open:!1},mutations:{toggle:function(t,e){t.open="boolean"==typeof e?e:!t.open}}},tabbar:{strict:!0,namespaced:!0,state:{index:1},mutations:{set:function(t,e){t.index=e}}}}},c={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-toolbar",[n("div",{staticClass:"left"},[t._t("left",[t.backLabel?n("v-ons-back-button",[t._v("\n        "+t._s(t.backLabel)+"\n      ")]):t._e()])],2),t._v(" "),n("div",{staticClass:"center"},[t._t("default",[t._v(t._s(t.title))])],2),t._v(" "),n("div",{staticClass:"right"},[t._t("right")],2)])},staticRenderFns:[]},v=n("VU/8")({props:["title","backLabel"]},c,!1,null,null,null).exports,u={data:function(){return{alertDialog2Visible:!1,title:"",message:""}},methods:{getImage:function(){var t=this;void 0!==navigator.camera?navigator.camera.getPicture(function(e){t.title="照片地址",t.message=e,t.alertDialog2Visible=!0},function(e){t.title="错误",t.message=e,t.alertDialog2Visible=!0},{quality:50,destinationType:navigator.camera.DestinationType.FILE_URI,sourceType:navigator.camera.PictureSourceType.CAMERA}):(this.title="错误",this.message="未加载相机插件",this.alertDialog2Visible=!0)}}},d={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("div",{staticClass:"camera"},[n("v-ons-alert-dialog",{attrs:{modifier:"rowfooter",title:t.title,footer:{"确定":function(){return t.alertDialog2Visible=!1}},visible:t.alertDialog2Visible},on:{"update:visible":function(e){t.alertDialog2Visible=e}}},[t._v("\n      "+t._s(t.message)+"\n    ")]),t._v(" "),n("div",{staticClass:"focus",on:{click:function(e){t.getImage()}}})],1)])},staticRenderFns:[]};var m=n("VU/8")(u,d,!1,function(t){n("nrsG")},"data-v-249d50b9",null).exports,p=n("Gu7T"),f=n.n(p),b={data:function(){return{state:"initial",kittens:this.getRandomData(),ratio:0}},methods:{onPull:function(t){this.ratio=t},onAction:function(t){var e=this;setTimeout(function(){e.kittens=[].concat(f()(e.kittens),[e.getRandomKitten()]),t()},1500)},getRandomName:function(){var t=["Oscar","Max","Tiger","Sam","Misty","Simba","Coco","Chloe","Lucy","Missy"];return t[Math.floor(Math.random()*t.length)]},getRandomUrl:function(){return"https://placekitten.com/g/"+(40+Math.floor(20*Math.random()))+"/"+(40+Math.floor(20*Math.random()))},getRandomKitten:function(){return{name:this.getRandomName(),url:this.getRandomUrl()}},getRandomData:function(){for(var t=[],e=0;e<8;e++)t.push(this.getRandomKitten());return t}}},h={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("custom-toolbar",t._b({},"custom-toolbar",t.toolbarInfo,!1)),t._v(" "),n("v-ons-pull-hook",{attrs:{action:t.onAction,"fixed-content":t.md,height:t.md?84:64,"on-pull":t.md&&t.onPull||null},on:{changestate:function(e){t.state=e.state}}},[t.md?n("div",{staticClass:"pull-hook-progress"},[n("v-ons-progress-circular",{style:{transform:"rotate("+t.ratio+"turn)"},attrs:{value:100*t.ratio,indeterminate:"action"===t.state}})],1):n("v-ons-icon",{staticClass:"pull-hook-spinner",attrs:{size:"22px",icon:"action"===t.state?"fa-spinner":"fa-arrow-down",rotate:"preaction"===t.state&&180,spin:"action"===t.state}})],1),t._v(" "),n("v-ons-list",[n("v-ons-list-header",[t._v("Pull to refresh")]),t._v(" "),t._l(t.kittens,function(e,i){return n("v-ons-list-item",{key:e.name+i},[n("div",{staticClass:"left"},[n("img",{staticClass:"list-item__thumbnail",attrs:{src:e.url}})]),t._v(" "),n("div",{staticClass:"center"},[t._v(t._s(e.name))])])})],2)],1)},staticRenderFns:[]};var _=n("VU/8")(b,h,!1,function(t){n("T03X")},null,null).exports,g={data:function(){return{dialogVisible:!1,alertDialogVisible:!1,toastVisible:!1,modalVisible:!1,popoverVisible:!1,actionSheetVisible:!1,timeoutID:0}},methods:{showModal:function(){var t=this;this.modalVisible=!0,clearTimeout(this.timeoutID),this.timeoutID=setTimeout(function(){t.modalVisible=!1},2e3)}}},x={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("custom-toolbar",t._b({},"custom-toolbar",t.toolbarInfo,!1),[n("v-ons-toolbar-button",{attrs:{slot:"right",id:"info-button"},on:{click:function(e){t.popoverVisible=!0}},slot:"right"},[t.md?n("v-ons-icon",{attrs:{icon:"md-more-vert"}}):n("span",[t._v("More")])],1)],1),t._v(" "),t.md?n("v-ons-fab",{attrs:{position:"bottom right"}},[n("v-ons-icon",{attrs:{icon:"md-face"}})],1):t._e(),t._v(" "),n("v-ons-list-title",[t._v("Notifications")]),t._v(" "),n("v-ons-list",{attrs:{modifier:"inset"}},[n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.$ons.notification.alert("Hello, world!")}}},[n("div",{staticClass:"center"},[t._v("\n        Alert\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.$ons.notification.confirm("Are you ready?")}}},[n("div",{staticClass:"center"},[t._v("\n        Simple Confirmation\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.$ons.notification.prompt("What is your name?")}}},[n("div",{staticClass:"center"},[t._v("\n        Prompt\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.$ons.notification.toast("Hi there!",{buttonLabel:"Dismiss",timeout:1500})}}},[n("div",{staticClass:"center"},[t._v("\n        Toast\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.$ons.openActionSheet({buttons:["Label 1","Label 2","Label 3","Cancel"],destructive:2,cancelable:!0})}}},[n("div",{staticClass:"center"},[t._v("\n        Action/Bottom Sheet\n      ")])])],1),t._v(" "),n("v-ons-list-title",[t._v("Components")]),t._v(" "),n("v-ons-list",{attrs:{modifier:"inset"}},[n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.dialogVisible=!0}}},[n("div",{staticClass:"center"},[t._v("\n        Simple Dialog\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.alertDialogVisible=!0}}},[n("div",{staticClass:"center"},[t._v("\n        Alert Dialog\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.toastVisible=!0}}},[n("div",{staticClass:"center"},[t._v("\n        Toast (top)\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:t.showModal}},[n("div",{staticClass:"center"},[t._v("\n        Modal\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.popoverVisible=!0}}},[n("div",{staticClass:"center"},[t._v("\n        Popover - MD Menu\n      ")])]),t._v(" "),n("v-ons-list-item",{attrs:{tappable:"",modifier:"longdivider"},on:{click:function(e){t.actionSheetVisible=!0}}},[n("div",{staticClass:"center"},[t._v("\n        Action/Bottom Sheet\n      ")])])],1),t._v(" "),n("v-ons-dialog",{staticClass:"lorem-dialog",attrs:{cancelable:"",visible:t.dialogVisible},on:{"update:visible":function(e){t.dialogVisible=e}}},[n("v-ons-page",[n("v-ons-toolbar",[n("div",{staticClass:"center"},[t._v("Simple Dialog")])]),t._v(" "),n("p",{staticStyle:{"text-align":"center"}},[t._v("Lorem ipsum dolor")]),t._v(" "),n("p",{staticStyle:{"text-align":"center"}},[n("v-ons-button",{attrs:{modifier:"light"},on:{click:function(e){t.dialogVisible=!1}}},[t._v("Close")])],1)],1)],1),t._v(" "),n("v-ons-alert-dialog",{attrs:{cancelable:"",modifier:t.md?"":"rowfooter",title:"Hey!!",footer:{Wat:function(){return t.alertDialogVisible=!1},Hmm:function(){return t.alertDialogVisible=!1},Sure:function(){return t.alertDialogVisible=!1}},visible:t.alertDialogVisible},on:{"update:visible":function(e){t.alertDialogVisible=e}}},[t._v("\n    Lorem ipsum "),n("v-ons-icon",{attrs:{icon:"fa-commenting-o"}})],1),t._v(" "),n("v-ons-toast",{attrs:{visible:t.toastVisible,animation:"fall"}},[t._v("Supercalifragilisticexpialidocious"),n("button",{on:{click:function(e){t.toastVisible=!1}}},[t._v("No way")])]),t._v(" "),n("v-ons-modal",{attrs:{visible:t.modalVisible},on:{click:function(e){t.modalVisible=!1}}},[n("p",{staticStyle:{"text-align":"center"}},[t._v("\n      Loading "),n("v-ons-icon",{attrs:{icon:"fa-spinner",spin:""}}),t._v(" "),n("br"),n("br"),t._v("\n      Click or wait\n    ")],1)]),t._v(" "),n("v-ons-popover",{attrs:{cancelable:"",direction:"down","cover-target":"",target:"#info-button",visible:t.popoverVisible},on:{"update:visible":function(e){t.popoverVisible=e}}},[n("v-ons-list",t._l(["Call history","Import/export","New contact","Settings"],function(e){return n("v-ons-list-item",{key:e,attrs:{tappable:"",modifier:t.md?"nodivider":"longdivider"},on:{click:function(e){t.popoverVisible=!1}}},[n("div",{staticClass:"center"},[t._v(t._s(e))])])}))],1),t._v(" "),n("v-ons-action-sheet",{attrs:{visible:t.actionSheetVisible,cancelable:""},on:{"update:visible":function(e){t.actionSheetVisible=e}}},[n("v-ons-action-sheet-button",{attrs:{icon:"md-square-o"},on:{click:function(e){t.actionSheetVisible=!1}}},[t._v("Action 1")]),t._v(" "),n("v-ons-action-sheet-button",{attrs:{icon:"md-square-o"},on:{click:function(e){t.actionSheetVisible=!1}}},[t._v("Action 2")]),t._v(" "),n("v-ons-action-sheet-button",{attrs:{modifier:"destructive",icon:"md-square-o"},on:{click:function(e){t.actionSheetVisible=!1}}},[t._v("Action 3")]),t._v(" "),n("v-ons-action-sheet-button",{attrs:{icon:"md-square-o"},on:{click:function(e){t.actionSheetVisible=!1}}},[t._v("Cancel")])],1)],1)},staticRenderFns:[]};var k=n("VU/8")(g,x,!1,function(t){n("ppCg"),n("yL+3")},"data-v-2a3f4c04",null).exports,w={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("custom-toolbar",t._b({},"custom-toolbar",t.toolbarInfo,!1)),t._v(" "),n("section",{staticStyle:{margin:"16px"}},[n("v-ons-segment",{staticStyle:{width:"260px"}},[n("button",[t._v("First")]),t._v(" "),n("button",[t._v("Second")]),t._v(" "),n("button",[t._v("Third")])]),t._v(" "),n("br"),n("br"),t._v(" "),n("v-ons-button",{staticClass:"button-margin"},[t._v("Normal")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"quiet"}},[t._v("Quiet")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"outline"}},[t._v("Outline")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"cta"}},[t._v("Call to action")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"light"}},[t._v("Light")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"large"}},[t._v("Large")])],1),t._v(" "),n("section",{staticStyle:{margin:"16px"}},[n("v-ons-segment",{staticStyle:{width:"260px"},attrs:{disabled:""}},[n("button",[t._v("First")]),t._v(" "),n("button",[t._v("Second")]),t._v(" "),n("button",[t._v("Third")])]),t._v(" "),n("br"),n("br"),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{disabled:""}},[t._v("Normal")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"quiet",disabled:""}},[t._v("Quiet")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"outline",disabled:""}},[t._v("Outline")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"cta",disabled:""}},[t._v("Call to action")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"light",disabled:""}},[t._v("Light")]),t._v(" "),n("v-ons-button",{staticClass:"button-margin",attrs:{modifier:"large",disabled:""}},[t._v("Large")])],1),t._v(" "),n("v-ons-fab",{attrs:{position:"top right"}},[n("v-ons-icon",{attrs:{icon:"md-face"}})],1),t._v(" "),n("v-ons-fab",{attrs:{position:"bottom left"}},[n("v-ons-icon",{attrs:{icon:"md-car"}})],1),t._v(" "),n("v-ons-speed-dial",{attrs:{position:"bottom right",direction:"up",open:t.spdOpen},on:{"update:open":function(e){t.spdOpen=e}}},[n("v-ons-fab",[n("v-ons-icon",{attrs:{icon:"md-share"}})],1),t._v(" "),t._l(t.shareItems,function(e,i){return n("v-ons-speed-dial-item",{key:i,on:{click:function(e){t.$ons.notification.confirm("Share on "+i+"?")}}},[n("v-ons-icon",{attrs:{icon:e}})],1)})],2)],1)},staticRenderFns:[]};var C=n("VU/8")({data:function(){return{spdOpen:!1,shareItems:{Twitter:"md-twitter",Facebook:"md-facebook","Google+":"md-google-plus"}}}},w,!1,function(t){n("tgKX")},"data-v-baa692aa",null).exports,y={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("custom-toolbar",t._b({},"custom-toolbar",t.toolbarInfo,!1)),t._v(" "),n("v-ons-carousel",{attrs:{fullscreen:"",swipeable:"","auto-scroll":"",overscrollable:"",index:t.carouselIndex},on:{"update:index":function(e){t.carouselIndex=e}}},t._l(t.items,function(e,i){return n("v-ons-carousel-item",{key:i,staticClass:"carousel-item",style:{backgroundColor:e}},[n("div",{staticClass:"color-tag"},[t._v(t._s(i))])])})),t._v(" "),n("div",{staticClass:"dots"},t._l(Object.keys(t.items).length,function(e){return n("span",{key:e,on:{click:function(n){t.carouselIndex=e-1}}},[t._v("\n      "+t._s(t.carouselIndex===e-1?"●":"○")+"\n    ")])}))],1)},staticRenderFns:[]};var V=n("VU/8")({data:function(){return{carouselIndex:0,items:{gray:"gray",blue:"#085078",dark:"#373B44",orange:"#D38312"}}}},y,!1,function(t){n("Lwfw")},"data-v-00518c9c",null).exports,I={data:function(){return{list:[]}},beforeMount:function(){for(var t=0;t<30;t++)this.list.push(t)},methods:{loadMore:function(t){var e=this;setTimeout(function(){for(var n=0;n<10;n++)e.list.push(e.list.length+n);t()},600)},renderItem:function(t){return new i.a({template:'\n          <v-ons-list-item :key="index">\n            Item #{{ index }}\n          </v-ons-list-item>\n        ',data:function(){return{index:t}}})}}},S={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("custom-toolbar",t._b({},"custom-toolbar",t.toolbarInfo,!1),[t.md?t._e():n("v-ons-segment",{staticStyle:{width:"200px"},attrs:{"tabbar-id":"infinite-scroll-tabbar"}},[n("button",[t._v("Load More")]),t._v(" "),n("button",[t._v("Lazy Repeat")])])],1),t._v(" "),n("v-ons-tabbar",{attrs:{id:"infinite-scroll-tabbar",position:"auto"}},[n("template",{slot:"pages"},[n("v-ons-page",{attrs:{"infinite-scroll":t.loadMore}},[n("p",{staticClass:"intro"},[t._v("\n          Useful for loading more items when the scroll reaches the bottom of the page, typically after an asynchronous API call."),n("br"),n("br")]),t._v(" "),n("v-ons-list",t._l(t.list,function(e){return n("v-ons-list-item",{key:e},[t._v("\n            Item #"+t._s(e)+"\n          ")])})),t._v(" "),n("div",{staticClass:"after-list"},[n("v-ons-icon",{attrs:{icon:"fa-spinner",size:"26px",spin:""}})],1)],1),t._v(" "),n("v-ons-page",[n("p",{staticClass:"intro"},[t._v("\n          Automatically unloads items that are not visible and loads new ones. Useful when the list contains thousands of items."),n("br"),n("br")]),t._v(" "),n("v-ons-list",[n("v-ons-lazy-repeat",{attrs:{"render-item":t.renderItem,length:3e3}})],1)],1)],1),t._v(" "),n("v-ons-tab",{attrs:{label:"Load More"}}),t._v(" "),n("v-ons-tab",{attrs:{label:"Lazy Repeat",active:""}})],2)],1)},staticRenderFns:[]};var T=n("VU/8")(I,S,!1,function(t){n("n+iZ")},"data-v-899efe5a",null).exports,$={data:function(){return{progress:0,intervalID:0}},mounted:function(){var t=this;this.intervalID=setInterval(function(){100!==t.progress?t.progress++:clearInterval(t.intervalID)},40)}},L={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("custom-toolbar",t._b({},"custom-toolbar",t.toolbarInfo,!1)),t._v(" "),n("v-ons-progress-bar",{attrs:{value:t.progress}}),t._v(" "),n("section",{staticStyle:{margin:"40px 16px"}},[n("p",[t._v("\n      Progress: "+t._s(t.progress)+"%\n    ")]),t._v(" "),n("p",[n("v-ons-progress-bar",{attrs:{value:"20"}})],1),t._v(" "),n("p",[n("v-ons-progress-bar",{attrs:{value:"40","secondary-value":"80"}})],1),t._v(" "),n("p",[n("v-ons-progress-bar",{attrs:{indeterminate:""}})],1),t._v(" "),n("div",{staticStyle:{"text-align":"center",margin:"40px",color:"#666"}},[n("p",[n("v-ons-progress-circular",{attrs:{value:"20"}}),t._v(" "),n("v-ons-progress-circular",{attrs:{value:"40","secondary-value":"80"}}),t._v(" "),n("v-ons-progress-circular",{attrs:{indeterminate:""}})],1),t._v(" "),n("p",[n("v-ons-icon",{attrs:{icon:"ion-load-a",spin:"",size:"30px"}}),t._v(" "),n("v-ons-icon",{attrs:{icon:"ion-load-b",spin:"",size:"30px"}}),t._v(" "),n("v-ons-icon",{attrs:{icon:"ion-load-c",spin:"",size:"30px"}}),t._v(" "),n("v-ons-icon",{attrs:{icon:"ion-load-d",spin:"",size:"30px"}})],1),t._v(" "),n("p",[n("v-ons-icon",{attrs:{icon:"fa-spinner",spin:"",size:"26px"}}),t._v(" "),n("v-ons-icon",{attrs:{icon:"circle-o-notch",spin:"",size:"26px"}})],1),t._v(" "),n("p",[n("v-ons-icon",{attrs:{icon:"md-spinner",spin:"",size:"30px"}})],1)]),t._v(" "),n("p",[n("v-ons-button",{attrs:{modifier:"large"}},[n("v-ons-icon",{attrs:{icon:"ion-load-c",spin:"",size:"26px"}})],1),t._v(" "),n("br"),n("br"),t._v(" "),n("v-ons-button",{attrs:{modifier:"large",disabled:""}},[n("v-ons-icon",{attrs:{icon:"ion-load-c",spin:"",size:"26px"}})],1)],1)])],1)},staticRenderFns:[]},R=n("VU/8")($,L,!1,null,null,null).exports,U={data:function(){return{pages:[{component:_,label:"Pull Hook",desc:'Simple "pull to refresh" functionality to update data.'},{component:k,label:"Dialogs",desc:"Components and utility methods to display many types of dialogs."},{component:C,label:"Buttons",desc:"Different styles for buttons, floating action buttons and speed dials."},{component:V,label:"Carousel",desc:"Customizable carousel that can be optionally fullscreen."},{component:T,label:"Infinite Scroll",desc:'Two types of infinite lists: "Load More" and "Lazy Repeat".'},{component:R,label:"Progress",desc:"Linear progress, circular progress and spinners."}]}},methods:{push:function(t,e){this.$store.commit("navigator/push",{extends:t,data:function(){return{toolbarInfo:{backLabel:"Home",title:e}}}})}}},O={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("p",{staticClass:"intro"},[t._v("\n    This is a kitchen sink example that shows off the Vue bindings for Onsen UI."),n("br"),n("br")]),t._v(" "),t._l(t.pages,function(e){return n("v-ons-card",{key:e.label,on:{click:function(n){t.push(e.component,e.label)}}},[n("div",{staticClass:"title"},[t._v(t._s(e.label))]),t._v(" "),n("div",{staticClass:"content"},[t._v(t._s(e.desc))])])})],2)},staticRenderFns:[]};var D=n("VU/8")(U,O,!1,function(t){n("u6VT")},null,null).exports,M={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("v-ons-list",[n("v-ons-list-header",[t._v("Text input")]),t._v(" "),n("v-ons-list-item",{attrs:{modifier:t.md?"nodivider":""}},[n("div",{staticClass:"left"},[n("v-ons-icon",{staticClass:"list-item__icon",attrs:{icon:"md-face"}})],1),t._v(" "),n("label",{staticClass:"center"},[n("v-ons-input",{attrs:{float:"",maxlength:"20",placeholder:"Name"},model:{value:t.name,callback:function(e){t.name=e},expression:"name"}})],1)]),t._v(" "),n("v-ons-list-item",{attrs:{modifier:t.md?"nodivider":""}},[n("div",{staticClass:"left"},[n("v-ons-icon",{staticClass:"list-item__icon",attrs:{icon:"fa-question-circle-o"}})],1),t._v(" "),n("label",{staticClass:"center"},[n("v-ons-search-input",{attrs:{maxlength:"20",placeholder:"Search"},model:{value:t.name,callback:function(e){t.name=e},expression:"name"}})],1)]),t._v(" "),n("v-ons-list-item",[n("div",{staticClass:"right right-label"},[t._v("\n        Hello "+t._s(t.name||"anonymous")+"!"),n("v-ons-icon",{staticClass:"right-icon",attrs:{icon:"fa-hand-spock-o",size:"lg"}})],1)]),t._v(" "),n("v-ons-list-header",[t._v("Range slider")]),t._v(" "),n("v-ons-list-item",[t._v("\n      Adjust the volume:\n      "),n("v-ons-row",[n("v-ons-col",{staticStyle:{"text-align":"center","line-height":"31px"},attrs:{width:"40px"}},[n("v-ons-icon",{attrs:{icon:"md-volume-down"}})],1),t._v(" "),n("v-ons-col",[n("v-ons-range",{staticStyle:{width:"100%"},model:{value:t.volume,callback:function(e){t.volume=e},expression:"volume"}})],1),t._v(" "),n("v-ons-col",{staticStyle:{"text-align":"center","line-height":"31px"},attrs:{width:"40px"}},[n("v-ons-icon",{attrs:{icon:"md-volume-up"}})],1)],1),t._v("\n      Volume: "+t._s(t.volume)+" "),n("span",{directives:[{name:"show",rawName:"v-show",value:Number(t.volume)>80,expression:"Number(volume) > 80"}]},[t._v(" (careful, that's loud)")])],1),t._v(" "),n("v-ons-list-header",[t._v("Switches")]),t._v(" "),n("ons-list-item",[n("label",{staticClass:"center",attrs:{for:"switch1"}},[t._v("\n        Switch ("+t._s(t.switchOn?"on":"off")+")\n      ")]),t._v(" "),n("div",{staticClass:"right"},[n("v-ons-switch",{attrs:{"input-id":"switch1"},model:{value:t.switchOn,callback:function(e){t.switchOn=e},expression:"switchOn"}})],1)]),t._v(" "),n("ons-list-item",[n("label",{staticClass:"center",attrs:{for:"switch2"}},[t._v("\n        "+t._s(t.switchOn?"Enabled switch":"Disabled switch")+"\n      ")]),t._v(" "),n("div",{staticClass:"right"},[n("v-ons-switch",{attrs:{"input-id":"switch2",disabled:!t.switchOn}})],1)]),t._v(" "),n("v-ons-list-header",[t._v("Select")]),t._v(" "),n("ons-list-item",[n("div",{staticClass:"center"},[n("v-ons-select",{staticStyle:{width:"120px"},model:{value:t.selectedItem,callback:function(e){t.selectedItem=e},expression:"selectedItem"}},t._l(t.items,function(e){return n("option",{domProps:{value:e.value}},[t._v("\n            "+t._s(e.text)+"\n          ")])}))],1),t._v(" "),n("div",{staticClass:"right right-label"},[n("s",{directives:[{name:"show",rawName:"v-show",value:"Vue"!==t.selectedItem,expression:"selectedItem !== 'Vue'"}]},[t._v(t._s(t.selectedItem))]),t._v(" Vue is awesome!\n      ")])]),t._v(" "),n("v-ons-list-header",[t._v("Radio buttons")]),t._v(" "),t._l(t.vegetables,function(e,i){return n("v-ons-list-item",{key:e,attrs:{tappable:"",modifier:i===t.vegetables.length-1?"longdivider":""}},[n("label",{staticClass:"left"},[n("v-ons-radio",{attrs:{"input-id":"radio-"+i,value:e},model:{value:t.selectedVegetable,callback:function(e){t.selectedVegetable=e},expression:" selectedVegetable"}})],1),t._v(" "),n("label",{staticClass:"center",attrs:{for:"radio-"+i}},[t._v("\n        "+t._s(e)+"\n      ")])])}),t._v(" "),n("v-ons-list-item",[n("div",{staticClass:"center"},[t._v("\n        I love "+t._s(t.selectedVegetable)+"!\n      ")])]),t._v(" "),n("v-ons-list-header",[t._v("Checkboxes - "+t._s(t.checkedColors))]),t._v(" "),t._l(t.colors,function(e,i){return n("v-ons-list-item",{key:e,attrs:{tappable:""}},[n("label",{staticClass:"left"},[n("v-ons-checkbox",{attrs:{"input-id":"checkbox-"+i,value:e},model:{value:t.checkedColors,callback:function(e){t.checkedColors=e},expression:"checkedColors"}})],1),t._v(" "),n("label",{staticClass:"center",attrs:{for:"checkbox-"+i}},[t._v("\n        "+t._s(e)+"\n      ")])])})],2)],1)},staticRenderFns:[]};var A=n("VU/8")({data:function(){return{name:"",switchOn:!0,items:[{text:"Vue",value:"Vue"},{text:"React",value:"React"},{text:"Angular",value:"Angular"}],selectedItem:"Vue",vegetables:["Apples","Bananas","Oranges"],selectedVegetable:"Bananas",colors:["Red","Green","Blue"],checkedColors:["Green","Blue"],volume:25}}},M,!1,function(t){n("w2jz")},"data-v-d57df168",null).exports,E={template:'\n    <v-ons-page>\n      <custom-toolbar backLabel="Anim">\n        {{ animation }}\n      </custom-toolbar>\n      <p style="text-align: center">\n        Use the VOnsBackButton\n      </p>\n    </v-ons-page>\n    '},P={data:function(){return{animations:["none","default","slide-ios","slide-md","lift-ios","lift-md","fade-ios","fade-md"]}},methods:{transition:function(t){var e=this;this.$store.commit("navigator/options",{animation:t,callback:function(){return e.$store.commit("navigator/options",{})}}),this.$store.commit("navigator/push",{extends:E,data:function(){return{animation:t}}})}}},F={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("v-ons-list",[n("v-ons-list-header",[t._v("Transitions")]),t._v(" "),t._l(t.animations,function(e){return n("v-ons-list-item",{key:e,attrs:{modifier:"chevron"},on:{click:function(n){t.transition(e)}}},[t._v("\n      "+t._s(e)+"\n    ")])})],2)],1)},staticRenderFns:[]},z=n("VU/8")(P,F,!1,null,null,null).exports,N=function(t,e,n){return parseInt((1-n)*t+n*e,10)},H=[244,67,54],B=[30,136,229],q=[103,58,183],X={data:function(){return{shutUp:!this.md,showingTip:!1,colors:H,animationOptions:{},topPosition:0,tabs:[{label:this.md?null:"Camera",icon:"ion-camera, material:md-camera",page:m,theme:H,style:this.md?{maxWidth:"60px"}:{},top:-105},{label:"Home",icon:this.md?null:"ion-home",page:D,theme:H},{label:"Forms",icon:this.md?null:"ion-edit",page:A,theme:B},{label:"Anim",icon:this.md?null:"ion-film-marker",page:z,theme:q}]}},methods:{onSwipe:function(t,e){var n=this;this.animationOptions=e;var i=Math.floor(t),o=Math.ceil(t),s=t%1;this.colors=this.colors.map(function(t,e){return N(n.tabs[i].theme[e],n.tabs[o].theme[e],s)}),this.topPosition=N(this.tabs[i].top||0,this.tabs[o].top||0,s)},showTip:function(t,e){var n=this;this.shutUp||t&&t.swipe||this.showingTip||(this.showingTip=!0,this.$ons.notification.toast({message:e,buttonLabel:"Shut up!",timeout:2e3}).then(function(t){n.shutUp=0===t,n.showingTip=!1}))}},computed:{index:{get:function(){return this.$store.state.tabbar.index},set:function(t){this.$store.commit("tabbar/set",t)}},title:function(){return this.md?"Onsen UI":this.tabs[this.index].title||this.tabs[this.index].label},swipeTheme:function(){return this.md&&{backgroundColor:"rgb("+this.colors.join(",")+")",transition:"all "+(this.animationOptions.duration||0)+"s "+(this.animationOptions.timing||"")}},swipePosition:function(){return this.md&&{top:this.topPosition+"px",transition:"all "+(this.animationOptions.duration||0)+"s "+(this.animationOptions.timing||"")}}}},G={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",{style:t.swipePosition},[n("custom-toolbar",{style:t.swipeTheme,attrs:{modifier:"white-content"}},[t._v("\n    "+t._s(t.title)+"\n    "),n("v-ons-toolbar-button",{attrs:{slot:"right",modifier:"white-content"},on:{click:function(e){t.$store.commit("splitter/toggle"),t.showTip(null,"Try dragging from right edge!")}},slot:"right"},[n("v-ons-icon",{attrs:{icon:"ion-navicon, material:md-menu"}})],1)],1),t._v(" "),n("v-ons-tabbar",{attrs:{position:"auto",swipeable:"",modifier:t.md?"autogrow white-content":null,"on-swipe":t.md?t.onSwipe:null,"tabbar-style":t.swipeTheme,tabs:t.tabs,index:t.index},on:{"update:index":function(e){t.index=e},postchange:function(e){t.showTip(e,"Tip: Try swiping pages!")}}})],1)},staticRenderFns:[]};var j={methods:{loadView:function(t){this.$store.commit("tabbar/set",t+1),this.$store.commit("splitter/toggle")},loadLink:function(t){window.open(t,"_blank")}},data:function(){return{links:[{title:"Docs",icon:"ion-document-text",url:"https://onsen.io/v2/docs/guide/vue/"},{title:"Github",icon:"ion-social-github",url:"https://github.com/OnsenUI/OnsenUI"},{title:"Code",icon:"ion-code",url:"https://github.com/OnsenUI/vue-onsenui-kitchensink"},{title:"Forum",icon:"ion-chatboxes",url:"https://community.onsen.io/"},{title:"Twitter",icon:"ion-social-twitter",url:"https://twitter.com/Onsen_UI"}],access:[{title:"Home",icon:"ion-home, material:md-home"},{title:"Forms",icon:"ion-edit, material:md-edit"},{title:"Animations",icon:"ion-film-marker, material: md-movie-alt"}]}}},W={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("v-ons-page",{attrs:{modifier:"white"}},[i("div",{staticClass:"profile-pic"},[i("img",{attrs:{src:n("PWdv")}})]),t._v(" "),i("v-ons-list-title",[t._v("Access")]),t._v(" "),i("v-ons-list",t._l(t.access,function(e,n){return i("v-ons-list-item",{key:e.title,attrs:{modifier:t.md&&"nodivider"},on:{click:function(e){t.loadView(n)}}},[i("div",{staticClass:"left"},[i("v-ons-icon",{staticClass:"list-item__icon",attrs:{"fixed-width":"",icon:e.icon}})],1),t._v(" "),i("div",{staticClass:"center"},[t._v("\n        "+t._s(e.title)+"\n      ")]),t._v(" "),i("div",{staticClass:"right"},[i("v-ons-icon",{attrs:{icon:"fa-link"}})],1)])})),t._v(" "),i("v-ons-list-title",[t._v("Links")]),t._v(" "),i("v-ons-list",t._l(t.links,function(e){return i("v-ons-list-item",{key:e.title,attrs:{modifier:t.md&&"nodivider"},on:{click:function(n){t.loadLink(e.url)}}},[i("div",{staticClass:"left"},[i("v-ons-icon",{staticClass:"list-item__icon",attrs:{"fixed-width":"",icon:e.icon}})],1),t._v(" "),i("div",{staticClass:"center"},[t._v("\n        "+t._s(e.title)+"\n      ")]),t._v(" "),i("div",{staticClass:"right"},[i("v-ons-icon",{attrs:{icon:"fa-external-link"}})],1)])}))],1)},staticRenderFns:[]};var K={computed:{isOpen:{get:function(){return this.$store.state.splitter.open},set:function(t){this.$store.commit("splitter/toggle",t)}}},components:{AppTabbar:n("VU/8")(X,G,!1,function(t){n("EzCN")},null,null).exports,MenuPage:n("VU/8")(j,W,!1,function(t){n("4yMX"),n("err1")},"data-v-2573ee4f",null).exports}},Q={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-ons-page",[n("v-ons-splitter",[n("v-ons-splitter-side",{attrs:{swipeable:"",side:"right",collapse:"",width:"260px","swipe-target-width":t.md&&25,animation:t.md?"overlay":"reveal",open:t.isOpen},on:{"update:open":function(e){t.isOpen=e}}},[n("menu-page")],1),t._v(" "),n("v-ons-splitter-content",[n("app-tabbar")],1)],1)],1)},staticRenderFns:[]};var Z=n("VU/8")(K,Q,!1,function(t){n("LTIx")},null,null).exports,J={beforeCreate:function(){this.$store.commit("navigator/push",Z)},data:function(){return{shutUp:this.md}},computed:{pageStack:function(){return this.$store.state.navigator.stack},options:function(){return this.$store.state.navigator.options},borderRadius:function(){return!0}},methods:{storePop:function(){this.$store.commit("navigator/pop")},showPopTip:function(){var t=this;!this.shutUp&&this.$ons.notification.toast({message:"Try swipe-to-pop from left side!",buttonLabel:"Shut up!",timeout:2e3}).then(function(e){t.shutUp=0===e})}}},Y={render:function(){var t=this.$createElement;return(this._self._c||t)("v-ons-navigator",{class:{"border-radius":this.borderRadius},attrs:{swipeable:"","swipe-target-width":"50px","page-stack":this.pageStack,"pop-page":this.storePop,options:this.options},on:{postpush:this.showPopTip}})},staticRenderFns:[]},tt=n("VU/8")(J,Y,!1,null,null,null).exports;i.a.config.productionTip=!1,i.a.use(o.a),i.a.use(a.a),i.a.use(r.a),i.a.component("custom-toolbar",v),new i.a({el:"#app",render:function(t){return t(tt)},store:new o.a.Store(l),beforeCreate:function(){i.a.prototype.baseUrl="http://218.58.65.20:8080/",this.$ons.platform.select("android"),i.a.prototype.md=this.$ons.platform.isAndroid(),window.location.search.match(/iphonex/i)&&(document.documentElement.setAttribute("onsflag-iphonex-portrait",""),document.documentElement.setAttribute("onsflag-iphonex-landscape",""))}})},PWdv:function(t,e,n){t.exports=n.p+"static/img/vue-onsenui.2a12ffb.png"},T03X:function(t,e){},"Xrl+":function(t,e){},err1:function(t,e){},fLmE:function(t,e){},"n+iZ":function(t,e){},nrsG:function(t,e){},ppCg:function(t,e){},tgKX:function(t,e){},u6VT:function(t,e){},w2jz:function(t,e){},"yL+3":function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.d785d489e3400c1deead.js.map