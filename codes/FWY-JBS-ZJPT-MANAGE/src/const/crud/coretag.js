export const tableOption = {
	"border": true,
	"index": true,
	"indexLabel": "序号",
	"stripe": true,
	"menuAlign": "center",
	"align": "center",
	"dialogDirection": "rtl",
	"searchMenuSpan": 6,
	"column": [{
		"type": "input",
		"label": "标签名称",
		"prop": "name",
		search: true,
		span: 24,
		rules: [{
			required: true,
			message: "请输入名称",
			trigger: "blur"
		}],
	}, {
		"type": "input",
		"label": "标签描述",
		"prop": "detail"
	}, {
		"type": "input",
		"label": "创建时间",
		"prop": "createTime",
		editDisplay: false,
		addDisplay: false,
	}]
}
