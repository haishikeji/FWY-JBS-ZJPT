export const tableOption = {
	"border": true,
	"index": true,
	"indexLabel": "序号",
	"stripe": true,
	"menuAlign": "center",
	"align": "center",
	"dialogDirection": "rtl",
	"searchMenuSpan": 6,
	editBtn:false,
	"column": [{
		"type": "input",
		"label": "剧本名称",
		"prop": "dramaName",
		span:24,
		search:true,
		editDetail: true,
		addDisplay: false,
	}, {
		"type": "select",
		"label": "剧本名称",
		"prop": "sdid",
		search:true,
		span:24,
		hide:true,
		props: {
			label: 'name',
			value: 'id'
		},
		dicUrl: `/shop/coreshopdrama/list`,
		dicMethod: 'get',
		rules: [
			{
				required: true,
				message: '请选择剧本',
				trigger: 'blur'
			}
		]
	}, {
		"type": "select",
		"label": "DM名称",
		"prop": "dmid",
		search:true,
		span:24,
		props: {
			label: 'name',
			value: 'id'
		},
		dicUrl: `/coreDm/coredm/list`,
		dicMethod: 'get',
		rules: [
			{
				required: true,
				message: '请选择DM',
				trigger: 'blur'
			}
		]
	}]
}
