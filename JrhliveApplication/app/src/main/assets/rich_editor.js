/**
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var RE = {};

RE.currentSelection = {
    "startContainer": 0,
    "startOffset": 0,
    "endContainer": 0,
    "endOffset": 0};

var spanId="";

RE.editor = document.getElementById('editor');

document.addEventListener("selectionchange", function() { RE.backuprange(); });

// Initializations
RE.callback = function() {
   var ranges = [
         '\ud83c[\udf00-\udfff]',
         '\ud83d[\udc00-\ude4f]',
         '\ud83d[\ude80-\udeff]'
     ];

    var oldlen=RE.editor.innerHTML;
    var newlen=RE.editor.innerHTML.replace(new RegExp(ranges.join('|'), 'g'), '');

    if(oldlen!=newlen){
        RE.backuprange();
        RE.editor.innerHTML =  RE.editor.innerHTML.replace(new RegExp(ranges.join('|'), 'g'), '');
        RE.restorerange();
    }
    //RE.editor.focus();
    window.location.href = "re-callback://" + encodeURI(RE.getHtml());
}

RE.setHtml = function(contents) {
//    RE.editor.innerHTML = decodeURIComponent(contents.replace(/\+/g, '%20'));

RE.editor.innerHTML=contents;
}

RE.reset = function(){

RE.editor.innerHTML='';
}
RE.getHtml = function() {

    var ranges = [
        '\ud83c[\udf00-\udfff]',
        '\ud83d[\udc00-\ude4f]',
        '\ud83d[\ude80-\udeff]'
    ];

    return RE.editor.innerHTML.replace(new RegExp(ranges.join('|'), 'g'), '');
}
RE.setLetterSpace=function(space){
      RE.editor.style.letterSpacing=space+"px";
}
RE.setLineHeight=function(lineHeight){
      RE.editor.style.lineHeight=lineHeight+"px";
}


RE.getText = function() {
    return RE.editor.innerText;
}

RE.setBaseTextColor = function(color) {
    RE.editor.style.color  = color;
}

RE.setBaseFontSize = function(size) {
    RE.editor.style.fontSize = size;
}

RE.setPadding = function(left, top, right, bottom) {
  RE.editor.style.paddingLeft = left;
  RE.editor.style.paddingTop = top;
  RE.editor.style.paddingRight = right;
  RE.editor.style.paddingBottom = bottom;
}

RE.setBackgroundColor = function(color) {
    document.body.style.backgroundColor = color;
}

RE.setBackgroundImage = function(image) {
    RE.editor.style.backgroundImage = image;
}

RE.setWidth = function(size) {
    RE.editor.style.minWidth = size;
}

RE.setHeight = function(size) {
    RE.editor.style.height = size;
}

RE.setTextAlign = function(align) {
    RE.editor.style.textAlign = align;
}

RE.setVerticalAlign = function(align) {
    RE.editor.style.verticalAlign = align;
}

RE.setPlaceholder = function(placeholder) {
    RE.editor.setAttribute("placeholder", placeholder);
}

RE.undo = function() {
    document.execCommand('undo', false, null);
}

RE.redo = function() {
    document.execCommand('redo', false, null);
}

RE.setBold = function() {
    document.execCommand('bold', false, null);
}

RE.setItalic = function() {
    document.execCommand('italic', false, null);
}

RE.setSubscript = function() {
    document.execCommand('subscript', false, null);
}
RE.setTextIndent=function(indent){
   RE.editor.style.textIndent=indent+"em";
}
RE.setSuperscript = function() {
    document.execCommand('superscript', false, null);
}

RE.setStrikeThrough = function() {
    document.execCommand('strikeThrough', false, null);
}

RE.setUnderline = function() {
    document.execCommand('underline', false, null);
}

RE.setBullets = function() {
    document.execCommand('InsertUnorderedList', false, null);
}

RE.setNumbers = function() {
    document.execCommand('InsertOrderedList', false, null);
}

RE.setTextColor = function(color) {
    RE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('foreColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

RE.setTextBackgroundColor = function(color) {
    RE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('hiliteColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

RE.setFontSize = function(fontSize){
    document.execCommand("fontSize", false, fontSize);
}

RE.setHeading = function(heading,b) {
    if(b)
        document.execCommand('formatBlock', false, '<h'+heading+'>');
    else
        document.execCommand('formatBlock', false, '<p>');
}

RE.setIndent = function() {
    document.execCommand('indent', false, null);
}

RE.setOutdent = function() {
    document.execCommand('outdent', false, null);
}

RE.setJustifyLeft = function() {
    document.execCommand('justifyLeft', false, null);
}

RE.setJustifyCenter = function() {
    document.execCommand('justifyCenter', false, null);
}

RE.setJustifyRight = function() {
    document.execCommand('justifyRight', false, null);
}

RE.setBlockquote = function(b) {
    if(b)
        document.execCommand('formatBlock', false, '<blockquote>');
    else
        document.execCommand('formatBlock', false, '<p>');
}

//图片点击事件
RE.onImgClicked = function(){

  var imgs = document.getElementsByTagName("img");
  for(var i=0;i<imgs.length;i++){
  imgs[i].onclick = alert(imgs[i].src);
 }
}

//插入图片
RE.insertImage = function(url, alt) {
//    var html = '<img id="showImg" onclick=RE.onImgClicked('+url+')"src="' + url + '" alt="' + alt + '" /><hr align=center width=200 color=#aaaaaa size=1 /><br/>';
       var imghtml = "<img id = 'showImg' src="+url+">";
    RE.insertHTML(imghtml);

}
//插入视频
RE.insertVideo=function(url){

var html='<video class ="video_container" src="'+url+'" controls="controls"></video>';
RE.insertHTML(html);
}
//插入音频
RE.insertAudio=function(url,content){
var html='<audio src="'+url+'" controls="controls"></audio><p align=center style="color:#aaaaaa">'+content+'</p>';
RE.insertHTML(html);
}

//插入分割线
RE.insertHr = function() {
    var html = '<hr color=#e2e2e2 size=1 /><br/>';
    RE.insertHTML(html);
}
//插换行
RE.insertBr = function() {
    var html = '<br/>';
    RE.insertHTML(html);
}

RE.insertHTML = function(html) {
    RE.restorerange();
    document.execCommand('insertHTML', false, html);
}

RE.getContentLength=function (){
window.android.setContentLength(parseInt(RE.editor.innerText.length));
}

RE.insertLink = function(url, title) {
    RE.restorerange();
    var sel = document.getSelection();
    if (sel.toString().length == 0) {
        document.execCommand("insertHTML",false,"<a href='"+url+"'>"+title+"</a>");
    } else if (sel.rangeCount) {
       var el = document.createElement("a");
       el.setAttribute("href", url);
       el.setAttribute("title", title);

       var range = sel.getRangeAt(0).cloneRange();
       range.surroundContents(el);
       sel.removeAllRanges();
       sel.addRange(range);
   }
    RE.callback();
}

RE.setTodo = function(text) {
    var html = '<input type="checkbox" name="'+ text +'" value="'+ text +'"/> &nbsp;';
    document.execCommand('insertHTML', false, html);
}

RE.prepareInsert = function() {
    RE.backuprange();
}

RE.backuprange = function(){
    var selection = window.getSelection();
    if (selection.rangeCount > 0) {
      var range = selection.getRangeAt(0);
      RE.currentSelection = {
          "startContainer": range.startContainer,
          "startOffset": range.startOffset,
          "endContainer": range.endContainer,
          "endOffset": range.endOffset};
    }
}

RE.restorerange = function(){
    var selection = window.getSelection();
    selection.removeAllRanges();
    var range = document.createRange();
    range.setStart(RE.currentSelection.startContainer, RE.currentSelection.startOffset);
    range.setEnd(RE.currentSelection.endContainer, RE.currentSelection.endOffset);
    selection.addRange(range);
}

RE.enabledEditingItems = function(e) {
    var items = [];

    if (document.queryCommandState('bold')) {
        items.push('bold');
    }
    if (document.queryCommandState('italic')) {
        items.push('italic');
    }
    if (document.queryCommandState('subscript')) {
        items.push('subscript');
    }
    if (document.queryCommandState('superscript')) {
        items.push('superscript');
    }
    if (document.queryCommandState('strikeThrough')) {
        items.push('strikeThrough');
    }
    if (document.queryCommandState('underline')) {
        items.push('underline');
    }
    if (document.queryCommandState('insertOrderedList')) {
        items.push('orderedList');
    }
    if (document.queryCommandState('insertUnorderedList')) {
        items.push('unorderedList');
    }
    if (document.queryCommandState('justifyCenter')) {
        items.push('justifyCenter');
    }
    if (document.queryCommandState('justifyFull')) {
        items.push('justifyFull');
    }
    if (document.queryCommandState('justifyLeft')) {
        items.push('justifyLeft');
    }
    if (document.queryCommandState('justifyRight')) {
        items.push('justifyRight');
    }
    if (document.queryCommandState('insertHorizontalRule')) {
        items.push('horizontalRule');
    }
    var formatBlock = document.queryCommandValue('formatBlock');
    if (formatBlock.length > 0) {
        items.push(formatBlock);
    }

    if(e.which==13){
        items.push("enter");
    }


    var ranges = [
        '\ud83c[\udf00-\udfff]',
        '\ud83d[\udc00-\ude4f]',
        '\ud83d[\ude80-\udeff]'
    ];

    window.location.href = "re-state://" + encodeURI(items.join(','))+"@_@"+encodeURI(RE.getHtml());
}

RE.focus = function() {
    var range = document.createRange();
    range.selectNodeContents(RE.editor);
    range.collapse(false);
    var selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
    RE.editor.focus();
}

RE.blurFocus = function() {
    RE.editor.blur();
}

RE.removeFormat = function() {
    execCommand('removeFormat', false, null);
}

RE.searchText = function (str){
    var content= RE.getHtml().split(str);
   RE.editor.innerHTML=content.join('<span style="background: #f00">' + str + '</span>');
}
RE.replaceText = function (primaryWord,replaceWord){
    var content= RE.getHtml().split(primaryWord);
    RE.editor.innerHTML=content.join('<span style="background: #f00">' + replaceWord + '</span>');
}

// Event Listeners
RE.editor.addEventListener("input", RE.callback);
RE.editor.addEventListener("keyup", function(e) {
    //RE.enabledEditingItems(e);
   var KEY_LEFT = 37, KEY_RIGHT = 39;
   if (e.which == KEY_LEFT || e.which == KEY_RIGHT || e.which ==8 || e.which == 13) {
       RE.enabledEditingItems(e);
   }
});
RE.editor.addEventListener("click", RE.enabledEditingItems);

RE.getBlob = function(view){
return view.Blob;
}

RE.createHtmlDoc = function(html) {
		var dt = doc_impl.createDocumentType('html', null, null)
			, doc = doc_impl.createDocument("http://www.w3.org/1999/xhtml", "html", dt)
			, doc_el = doc.documentElement
			, head = doc_el.appendChild(doc.createElement("head"))
			, charset_meta = head.appendChild(doc.createElement("meta"))
			, title = head.appendChild(doc.createElement("title"))
			, body = doc_el.appendChild(doc.createElement("body"))
			, i = 0
			, len = html.childNodes.length
		;
		charset_meta.setAttribute("charset", html.ownerDocument.characterSet);
		for (; i < len; i++) {
			body.appendChild(doc.importNode(html.childNodes.item(i), true));
		}
		var title_text = guess_title(doc);
		if (title_text) {
			title.appendChild(doc.createTextNode(title_text));
		}
		return doc;
	}

RE.save = function(view){

var BB = RE.getBlob(view) ;
var xml_serializer = new XMLSerializer();
var doc = create_html_doc(RE.getHtml());
	saveAs(new BB([xml_serializer.serializeToString(doc)]
	,{type: "text/plain;charset=" + document.characterSet})
	,(html_filename.value || html_filename.placeholder) + ".xhtml"
	);

}

RE.saveTest=function(path){
var FileSaver = require('file-saver');
var blob = new Blob([RE.getHtml()], {type: "text/plain;charset=utf-8"});
FileSaver.saveAs(blob, path);
//
//var blob = new Blob(["Hello, world!"], {type: "text/plain;charset=utf-8"});
//saveAs(blob, path);

}


//替换选中文本内容，参数text为要替换的内容
RE.replaceSelectionText=function (title) {
    //非IE浏览器
    var titleText=title;
    var txt;
    if (window.getSelection) {
        var sel = window.getSelection();
        txt=sel.toString();
        var r = sel.getRangeAt(0); //即使已经执行了deleteFromDocument(), 这个函数仍然返回一个有效对象.
        sel.deleteFromDocument(); //清除选择的内容
        var selFrag = r.cloneContents(); //克隆选择的内容
        var timestamp = Date.parse( new Date());
        var span = document.createElement('span'); //生成一个插入对象
        span.id=timestamp;
        spanId=timestamp;
        span.innerHTML = txt; //设置这个对象的内容
        r.insertNode(span); //把对象插入到选区, 这个操作不会替换选择的内容, 而是追加到选区的后面, 所以如果需要普通粘贴的替换效果, 之前执行deleteFromDocument()函数.
    }
    android.callback(txt,titleText);
}

//根据id找到span标签,0为删除，1为修改
RE.getSpan=function(action,text){
   var span=document.getElementById(spanId);
   if(action==0){//删除操作
    span.style.backgroundColor="white";
    span.style.textDecorationLine="line-through";
//   var imgHtml = "<img class='icon_edit' vertical-align:super onclick='testAlert()'/>";
//    document.execCommand('insertHTML', false, imgHtml);
   }else{
    span.innerText=text;
    span.style.backgroundColor="red";
//    var imgHtml = "<img class='icon_edit' vertical-align:super onclick='testAlert()'/>";
//    document.execCommand('insertHTML', false, imgHtml);

   }
}





