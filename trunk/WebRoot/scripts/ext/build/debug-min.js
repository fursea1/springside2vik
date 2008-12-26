/*
 * Ext JS Library 1.1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://www.extjs.com/license
 */

Ext.debug={init:function(){var CP=Ext.ContentPanel;var bd=Ext.get(document.body);var dlg=new Ext.LayoutDialog("x-debug-browser",{autoCreate:true,width:800,height:450,title:"Ext Debug Console &amp; Inspector",proxyDrag:true,shadow:true,center:{alwaysShowTabs:true},constraintoviewport:false});dlg.el.swallowEvent("click");var mainLayout=dlg.getLayout();mainLayout.beginUpdate();var clayout=mainLayout.add("center",new Ext.debug.InnerLayout("x-debug-console",400,{title:"Debug Console"}));var ilayout=mainLayout.add("center",new Ext.debug.InnerLayout("x-debug-inspector",250,{title:"DOM Inspector"}));var scriptPanel=clayout.add("east",new CP({autoCreate:{tag:"div",children:[{tag:"div"},{tag:"textarea"}]},fitContainer:true,fitToFrame:true,title:"Script Console",autoScroll:Ext.isGecko,setSize:function(w,h){Ext.ContentPanel.prototype.setSize.call(this,w,h);if(Ext.isGecko&&Ext.isStrict){var s=this.adjustForComponents(w,h);this.resizeEl.setSize(s.width-2,s.height-2)}}}));var sel=scriptPanel.el;var script=sel.child("textarea");scriptPanel.resizeEl=script;var sctb=scriptPanel.toolbar=new Ext.Toolbar(sel.child("div"));sctb.add({text:"Run",handler:function(){var s=script.dom.value;if(trap.checked){try{var rt=eval(s);Ext.debug.dump(rt===undefined?"(no return)":rt)}catch(e){Ext.debug.log(e.message||e.descript)}}else{var rt=eval(s);Ext.debug.dump(rt===undefined?"(no return)":rt)}}},{text:"Clear",handler:function(){script.dom.value="";script.dom.focus()}});var trap=Ext.DomHelper.append(sctb.el,{tag:"input",type:"checkbox",checked:"checked"});trap.checked=true;sctb.add("-",trap,"Trap Errors");var stylesGrid=new Ext.grid.PropertyGrid(bd.createChild(),{nameText:"Style",enableHdMenu:false,enableColumnResize:false});var stylePanel=ilayout.add("east",new Ext.GridPanel(stylesGrid,{title:"(No element selected)"}));stylesGrid.render();stylesGrid.getView().mainHd.setDisplayed(false);clayout.tbar.add({text:"Clear",handler:function(){Ext.debug.console.jsonData=[];Ext.debug.console.refresh()}});var treeEl=ilayout.main.getEl();var tb=ilayout.tbar;var inspectIgnore,inspecting;function inspectListener(e,t){if(!inspectIgnore.contains(e.getPoint())){findNode(t)}}function stopInspecting(e,t){if(!inspectIgnore.contains(e.getPoint())){inspect.toggle(false);if(findNode(t)!==false){e.stopEvent()}}}function stopInspectingEsc(e,t){if(e.getKey()==e.ESC){inspect.toggle(false)}}var inspect=tb.addButton({text:"Inspect",enableToggle:true,pressed:false,toggleHandler:function(n,pressed){var d=Ext.get(document);if(pressed){d.on("mouseover",inspectListener,window,{buffer:50});d.on("mousedown",stopInspecting);d.on("keydown",stopInspectingEsc);inspectIgnore=dlg.el.getRegion();inspecting=true}else{d.un("mouseover",inspectListener);d.un("mousedown",stopInspecting);d.on("keydown",stopInspectingEsc);inspecting=false;var n=tree.getSelectionModel().getSelectedNode();if(n&&n.htmlNode){onNodeSelect(tree,n,false)}}}});tb.addSeparator();var frameEl=tb.addButton({text:"Highlight Selection",enableToggle:true,pressed:false,toggleHandler:function(n,pressed){var n=tree.getSelectionModel().getSelectedNode();if(n&&n.htmlNode){n[pressed?"frame":"unframe"]()}}});tb.addSeparator();var reload=tb.addButton({text:"Refresh Children",disabled:true,handler:function(){var n=tree.getSelectionModel().getSelectedNode();if(n&&n.reload){n.reload()}}});tb.add("-",{text:"Collapse All",handler:function(){tree.root.collapse(true)}});mainLayout.endUpdate();mainLayout.getRegion("center").showPanel(0);stylesGrid.on("propertychange",function(s,name,value){var node=stylesGrid.treeNode;if(styles){node.htmlNode.style[name]=value}else{node.htmlNode[name]=value}node.refresh(true)});var stb=new Ext.Toolbar(stylesGrid.view.getHeaderPanel(true));var swap=stb.addButton({text:"DOM Attributes",menu:{items:[new Ext.menu.CheckItem({id:"dom",text:"DOM Attributes",checked:true,group:"xdb-styles"}),new Ext.menu.CheckItem({id:"styles",text:"CSS Properties",group:"xdb-styles"})]}});swap.menu.on("click",function(){styles=swap.menu.items.get("styles").checked;showAll[styles?"show":"hide"]();swap.setText(styles?"CSS Properties":"DOM Attributes");var n=tree.getSelectionModel().getSelectedNode();if(n){onNodeSelect(tree,n)}});var addStyle=stb.addButton({text:"Add",disabled:true,handler:function(){Ext.MessageBox.prompt("Add Property","Property Name:",function(btn,v){var store=stylesGrid.store.store;if(btn=="ok"&&v&&!store.getById(v)){var r=new Ext.grid.PropertyRecord({name:v,value:""},v);store.add(r);stylesGrid.startEditing(store.getCount()-1,1)}})}});var showAll=stb.addButton({text:"Computed Styles",hidden:true,pressed:false,enableToggle:true,toggleHandler:function(){var n=tree.getSelectionModel().getSelectedNode();if(n){onNodeSelect(tree,n)}}});var styles=false,hnode;var nonSpace=/^\s*$/;var html=Ext.util.Format.htmlEncode;var ellipsis=Ext.util.Format.ellipsis;var styleRe=/\s?([a-z\-]*)\:([^;]*)(?:[;\s\n\r]*)/gi;function findNode(n){if(!n||n.nodeType!=1||n==document.body||n==document){return false}var pn=[n],p=n;while((p=p.parentNode)&&p.nodeType==1&&p.tagName.toUpperCase()!="HTML"){pn.unshift(p)}var cn=hnode;for(var i=0,len=pn.length;i<len;i++){cn.expand();cn=cn.findChild("htmlNode",pn[i]);if(!cn){return false}}cn.select();var a=cn.ui.anchor;treeEl.dom.scrollTop=Math.max(0,a.offsetTop-10);cn.highlight();return true}function nodeTitle(n){var s=n.tagName;if(n.id){s+="#"+n.id}else{if(n.className){s+="."+n.className}}return s}function onNodeSelect(t,n,last){if(last&&last.unframe){last.unframe()}var props={};if(n&&n.htmlNode){if(frameEl.pressed){n.frame()}if(inspecting){return }addStyle.enable();reload.setDisabled(n.leaf);var dom=n.htmlNode;stylePanel.setTitle(nodeTitle(dom));if(styles&&!showAll.pressed){var s=dom.style?dom.style.cssText:"";if(s){var m;while((m=styleRe.exec(s))!=null){props[m[1].toLowerCase()]=m[2]}}}else{if(styles){var cl=Ext.debug.cssList;var s=dom.style,fly=Ext.fly(dom);if(s){for(var i=0,len=cl.length;i<len;i++){var st=cl[i];var v=s[st]||fly.getStyle(st);if(v!=undefined&&v!==null&&v!==""){props[st]=v}}}}else{for(var a in dom){var v=dom[a];if((isNaN(a+10))&&v!=undefined&&v!==null&&v!==""&&!(Ext.isGecko&&a[0]==a[0].toUpperCase())){props[a]=v}}}}}else{if(inspecting){return }addStyle.disable();reload.disabled()}stylesGrid.setSource(props);stylesGrid.treeNode=n;stylesGrid.view.fitColumns()}var filterIds="^(?:";var eds=stylesGrid.colModel.editors;for(var edType in eds){filterIds+=eds[edType].id+"|"}Ext.each([dlg.shim?dlg.shim.id:"noshim",dlg.proxyDrag.id],function(id){filterIds+=id+"|"});filterIds+=dlg.el.id;filterIds+=")$";var filterRe=new RegExp(filterIds);var loader=new Ext.tree.TreeLoader();loader.load=function(n,cb){var isBody=n.htmlNode==bd.dom;var cn=n.htmlNode.childNodes;for(var i=0,c;c=cn[i];i++){if(isBody&&filterRe.test(c.id)){continue}if(c.nodeType==1){n.appendChild(new Ext.debug.HtmlNode(c))}else{if(c.nodeType==3&&!nonSpace.test(c.nodeValue)){n.appendChild(new Ext.tree.TreeNode({text:"<em>"+ellipsis(html(String(c.nodeValue)),35)+"</em>",cls:"x-tree-noicon"}))}}}cb()};var tree=new Ext.tree.TreePanel(treeEl,{enableDD:false,loader:loader,lines:false,rootVisible:false,animate:false,hlColor:"ffff9c"});tree.getSelectionModel().on("selectionchange",onNodeSelect,null,{buffer:250});var root=tree.setRootNode(new Ext.tree.TreeNode("Ext"));hnode=root.appendChild(new Ext.debug.HtmlNode(document.getElementsByTagName("html")[0]));tree.render();Ext.debug.console=new Ext.JsonView(clayout.main.getEl(),"<pre><xmp>> {msg}</xmp></pre>");Ext.debug.console.jsonData=[];Ext.debug.dialog=dlg},show:function(){var A=Ext.debug;if(!A.dialog){A.init()}if(!A.dialog.isVisible()){A.dialog.show()}},hide:function(){if(Ext.debug.dialog){Ext.debug.dialog.hide()}},log:function(E,C,D){Ext.debug.show();var B="";for(var F=0,A=arguments.length;F<A;F++){B+=(F==0?"":", ")+arguments[F]}var G=Ext.debug.console;G.jsonData.unshift({msg:B});G.refresh()},logf:function(D,C,A,B){Ext.debug.log(String.format.apply(String,arguments))},dump:function(D){if(typeof D=="string"||typeof D=="number"||typeof D=="undefined"||D instanceof Date){Ext.debug.log(D)}else{if(!D){Ext.debug.log("null")}else{if(typeof D!="object"){Ext.debug.log("Unknown return type")}else{if(D instanceof Array){Ext.debug.log("["+D.join(",")+"]")}else{var A=["{\n"];for(var B in D){var E=typeof D[B];if(E!="function"&&E!="object"){A.push(String.format("  {0}: {1},\n",B,D[B]))}}var C=A.join("");if(C.length>3){C=C.substr(0,C.length-2)}Ext.debug.log(C+"\n}")}}}}},_timers:{},time:function(A){A=A||"def";Ext.debug._timers[A]=new Date().getTime()},timeEnd:function(B,D){var C=new Date().getTime();B=B||"def";var A=String.format("{0} ms",C-Ext.debug._timers[B]);Ext.debug._timers[B]=new Date().getTime();if(D!==false){Ext.debug.log("Timer "+(B=="def"?A:B+": "+A))}return A}};Ext.debug.HtmlNode=function(){var D=Ext.util.Format.htmlEncode;var B=Ext.util.Format.ellipsis;var A=/^\s*$/;var C=[{n:"id",v:"id"},{n:"className",v:"class"},{n:"name",v:"name"},{n:"type",v:"type"},{n:"src",v:"src"},{n:"href",v:"href"}];function F(J){for(var H=0,I;I=J.childNodes[H];H++){if(I.nodeType==1){return true}}return false}function E(I,L){var P=I.tagName.toLowerCase();var O="&lt;"+P;for(var J=0,K=C.length;J<K;J++){var M=C[J];var N=I[M.n];if(N&&!A.test(N)){O+=" "+M.v+"=&quot;<i>"+D(N)+"</i>&quot;"}}var H=I.style?I.style.cssText:"";if(H){O+=" style=&quot;<i>"+D(H.toLowerCase())+"</i>&quot;"}if(L&&I.childNodes.length>0){O+="&gt;<em>"+B(D(String(I.innerHTML)),35)+"</em>&lt;/"+P+"&gt;"}else{if(L){O+=" /&gt;"}else{O+="&gt;"}}return O}var G=function(J){var I=!F(J);this.htmlNode=J;this.tagName=J.tagName.toLowerCase();var H={text:E(J,I),leaf:I,cls:"x-tree-noicon"};G.superclass.constructor.call(this,H);this.attributes.htmlNode=J;if(!I){this.on("expand",this.onExpand,this);this.on("collapse",this.onCollapse,this)}};Ext.extend(G,Ext.tree.AsyncTreeNode,{cls:"x-tree-noicon",preventHScroll:true,refresh:function(I){var H=!F(this.htmlNode);this.setText(E(this.htmlNode,H));if(I){Ext.fly(this.ui.textNode).highlight()}},onExpand:function(){if(!this.closeNode&&this.parentNode){this.closeNode=this.parentNode.insertBefore(new Ext.tree.TreeNode({text:"&lt;/"+this.tagName+"&gt;",cls:"x-tree-noicon"}),this.nextSibling)}else{if(this.closeNode){this.closeNode.ui.show()}}},onCollapse:function(){if(this.closeNode){this.closeNode.ui.hide()}},render:function(H){G.superclass.render.call(this,H)},highlightNode:function(){},highlight:function(){},frame:function(){this.htmlNode.style.border="1px solid #0000ff"},unframe:function(){this.htmlNode.style.border=""}});return G}();Ext.debug.InnerLayout=function(G,B,A){var C=Ext.DomHelper.append(document.body,{id:G});var D=new Ext.BorderLayout(C,{north:{initialSize:28},center:{titlebar:false},east:{split:true,initialSize:B,titlebar:true}});Ext.debug.InnerLayout.superclass.constructor.call(this,D,A);D.beginUpdate();var E=D.add("north",new Ext.ContentPanel({autoCreate:true,fitToFrame:true}));this.main=D.add("center",new Ext.ContentPanel({autoCreate:true,fitToFrame:true,autoScroll:true}));this.tbar=new Ext.Toolbar(E.el);var F=E.resizeEl=E.el.child("div.x-toolbar");F.setStyle("border-bottom","0 none");D.endUpdate(true)};Ext.extend(Ext.debug.InnerLayout,Ext.NestedLayoutPanel,{add:function(){return this.layout.add.apply(this.layout,arguments)}});Ext.debug.cssList=["background-color","border","border-color","border-spacing","border-style","border-top","border-right","border-bottom","border-left","border-top-color","border-right-color","border-bottom-color","border-left-color","border-top-width","border-right-width","border-bottom-width","border-left-width","border-width","bottom","color","font-size","font-size-adjust","font-stretch","font-style","height","left","letter-spacing","line-height","margin","margin-top","margin-right","margin-bottom","margin-left","marker-offset","max-height","max-width","min-height","min-width","orphans","outline","outline-color","outline-style","outline-width","overflow","padding","padding-top","padding-right","padding-bottom","padding-left","quotes","right","size","text-indent","top","width","word-spacing","z-index","opacity","outline-offset"];if(typeof console=="undefined"){console=Ext.debug}Ext.EventManager.on(window,"load",function(){Ext.get(document).on("keydown",function(A){if(A.ctrlKey&&A.shiftKey&&A.getKey()==A.HOME){Ext.debug.show()}})});Ext.print=Ext.log=Ext.debug.log;Ext.printf=Ext.logf=Ext.debug.logf;Ext.dump=Ext.debug.dump;Ext.timer=Ext.debug.time;Ext.timerEnd=Ext.debug.timeEnd;