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
		"label": "版本号",
		"prop": "version",
		span:24,
		rules: [{
			required: true,
			message: "请输入更新版本号",
			trigger: "blur"
		}]
	}, {
		"type": "textarea",
		"label": "更新内容",
		"prop": "content",
		span:24,
		rules: [{
			required: true,
			message: "请输入更新内容",
			trigger: "blur"
		}],
		search:true,
	}, {
		"type": "select",
		"label": "所属APP",
		"prop": "aid",
		span:24,
		props: {
			label: 'name',
			value: 'id'
		},
		dicUrl: `/upms/sysapp/list`,
		dicMethod:'get',
		rules: [{
			required: true,
			message: "请选择所属APP",
			trigger: "change"
		}],
		search:true,
	}, {
		"type": "input",
		"label": "添加时间",
		"prop": "createTime",
		addDisplay: false,
		editDisplay: false
	}]
}
