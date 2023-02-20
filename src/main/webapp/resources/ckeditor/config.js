/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.height = 600;
	/*config.toolbar = myToolbar;*/
	config.toolbar = [
			[ 'Source', '-', 'Cut', 'Copy', 'Paste', 'PasteText',
					'PasteFromWord', 'Undo', 'Redo', 'SelectAll',
					'RemoveFormat' ],
			'/',
			[ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript',
					'Superscript' ],
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
			[ 'NumberedList', 'BulletedList', 'Outdent', 'Indent',
					'Blockquote', 'CreateDiv' ],
			[ 'Image', 'Ajaximage', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar',
					'PageBreak' ],
			[ 'Styles', 'Format', 'Font', 'FontSize', 'TextColor', 'BGColor',
					'Maximize', 'ShowBlocks' ] ];
;
	// config.uiColor = '#9AB8F3';
	/* config.uiColor = '#D3D3D3'; */
	config.enterMode = '2'; //엔터키 태그 1:<p>, 2:<br>, 3:<div>
	config.font_defaultLabel = '굴림'; //기본글씨
	config.font_names = ' 굴림; 나눔바른고딕; 돋움; 궁서; HY견고딕; HY견명조; 휴먼둥근헤드라인; 휴먼매직체; 휴먼모음T; 휴먼아미체; 휴먼엑스포; 휴먼옛체; 휴먼편지체;' + CKEDITOR.config.font_names;
	config.allowedContent= true; //필터링 해제
	/*config.filebrowserUploadUrl = '/ckeditor2/upload.jsp?realUrl=/data/ckimages/&realDir=d:/ccas2012/data/ckimages/';*/
	//config.filebrowserUploadUrl = '/admin/board/cmm/uploadCkeditor.do?${_csrf.parameterName}=${_csrf.token}';
	config.extraPlugins = 'ajaximage';
	config.extraAllowedContent = 'img[src,alt]';
};
