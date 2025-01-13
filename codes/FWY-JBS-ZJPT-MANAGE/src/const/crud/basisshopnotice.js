export const tableOption = {
	"border": true,
	"index": true,
	"indexLabel": "序号",
	"stripe": true,
	"menuAlign": "center",
	"align": "center",
	"dialogDirection": "rtl",
	"searchMenuSpan": 6,
	labelWidth:150,
	"column": [{
		"type": "input",
		"label": "添加时间",
		"prop": "createTime",
		width: 150,
		addDisplay:false,
		editDisplay:false,
	}, {
		"type": "textarea",
		"label": "店铺通知内容",
		"prop": "content",
		span:24,
	}]
}
