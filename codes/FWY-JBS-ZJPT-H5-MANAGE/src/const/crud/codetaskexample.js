export const tableOption = {
	align: "center",
	index: true,
	border: true,
	column: [
		{prop: "taskId", label: "任务ID", display: false},
		{prop: "appid", label: "appId", display: false},
		{prop: "appName", label: "小程序名称", display: false, search: true},
		{prop: "createTime", label: "创建时间", display: false},
		{prop: "status", label: "任务状态", display: false,
			"dicData": [
				{
					label: "空状态",
					value: 0
				},
				{
					label: "待提交审核",
					value: 1
				},
				{
					label: "审核中",
					value: 2
				},
				{
					label: "审核通过",
					value: 3
				},
				{
					label: "审核未通过",
					value: 4
				},
				{
					label: "任务撤回",
					value: 5
				}
			],},
		{prop: "endTime", label: "最后更新时间", display: false},
		{prop: "useStatus", label: "使用状态", display: false,
			"dicData": [
				{
					label: "未发布",
					value: 1
				},
				{
					label: "发布成功",
					value: 2
				}
			],}
	],
	gutter: 0,
	stripe: true,
	menuBtn: true,
	addBtn: true,
	editBtn: true,
	emptyBtn: true,
	emptyText: "清空",
	menuAlign: "center",
	submitBtn: true,
	indexLabel: "序号",
	labelWidth: 120,
	searchLabelWidth: 120,
	submitText: "提交",
	labelSuffix: "：",
	menuPosition: "center",
	labelPosition: "left",
	searchMenuSpan: 6
};
