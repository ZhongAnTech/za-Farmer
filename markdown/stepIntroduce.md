## 步骤介绍

- [Click 点击](#click)
- [Input 输入](#input)
- [Swipe 滑动](#swipe)
- [PressKey 按键](#presskey)
- [LongClick 长点击](#longclick)
- [ClickByPoint 坐标点击](#clickbypoint)
- [OpenApplication 启动应用](#openapplication)
- [AssertExist 元素是否存在](#assertexist)
- [WaitUntilGone 等待元素消失](#waituntilgone)

#### Click
点击某个页面元素
* **匹配元素** 所有属性都存在精准、包含、正则三种匹配模式。（例：elementText 、elementTextContains、elementTextPattern ）
    * **elementId** 匹配元素的 resource-id。
    * **elementText** 匹配元素的 text。
    * **elementDesc** 匹配元素的 content-desc。
    * **elementClazz** 匹配元素的 class。
    * **elementPackage** 匹配元素的 package。
    * **elementTextDesc** 匹配元素的 text 或 content-desc。
* **order** 如果有多个匹配元素，匹配第几个元素。默认值：`0`
* **scrollFind** 当元素不在页面中，是否启用向上滑动继续查找。默认值：`1`
* **scrollCount** 滑动查找的次数。默认值：`5`
* **waitTime** 执行完成后固定等待的时间（ms） 默认值: `1000`
* **toastMessage** 操作完成后检测toast信息
* **autoPermit** 处理权限弹窗 默认值: `false`

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action Click \
-e step-elementTextContains 设置  \
-e step-elementClazz android.widget.TextView  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```

#### Input
在输入框中输入文本内容
* **匹配元素** 所有属性都存在精准、包含、正则三种匹配模式。（例：elementText 、elementTextContains、elementTextPattern ）
    * **elementId** 匹配元素的 resource-id。
    * **elementText** 匹配元素的 text。
    * **elementDesc** 匹配元素的 content-desc。
    * **elementClazz** 匹配元素的 class。
    * **elementPackage** 匹配元素的 package。
    * **elementTextDesc** 匹配元素的 text 或 content-desc。
* **order** 如果有多个匹配元素，匹配第几个元素。默认值：`0`
* **scrollFind** 当元素不在页面中，是否启用向上滑动继续查找。默认值：`1`
* **scrollCount** 滑动查找的次数。默认值：`5`
* **waitTime** 执行完成后固定等待的时间（ms） 默认值: `1000`
* **toastMessage** 操作完成后检测toast信息
* **reText** 输入文本内容
* **autoPermit** 处理权限弹窗 默认值: `false`

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action Input \
-e step-reText 随便输入  \
-e step-elementId com.android.quicksearchbox:id/query_text  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```
#### Swipe
在页面上进行上、下、左、右及坐标滑动
* **方向滑动**
    * **direction**  参数可选值: `up` `down` `left` `right`
* **坐标滑动** 
    * **startX** 起始坐标X
    * **startY** 起始坐标Y
    * **endX** 终点坐标X
    * **endY** 终点坐标Y
 * **toastMessage** 操作完成后检测toast信息

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action Swipe \
-e step-direction up  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```
```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action swipe \
-e step-startX 500  \
-e step-startY 800  \
-e step-endX 500  \
-e step-endY 200  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```

#### PressKey
触发按键进行不同的按键操作
* **keyCode** 安卓keycode
* **toastMessage** 操作完成后检测toast信息


```bash
#触发Home键
adb shell am instrument -w -r -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action PressKey \
-e step-keyCode 3 \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```
#### LongClick
长点击某个页面元素  默认值:`500毫秒`
* **匹配元素** 所有属性都存在精准、包含、正则三种匹配模式。（例：elementText 、elementTextContains、elementTextPattern ）
    * **elementId** 匹配元素的 resource-id。
    * **elementText** 匹配元素的 text。
    * **elementDesc** 匹配元素的 content-desc。
    * **elementClazz** 匹配元素的 class。
    * **elementPackage** 匹配元素的 package。
    * **elementTextDesc** 匹配元素的 text 或 content-desc。
* **order** 如果有多个匹配元素，匹配第几个元素。默认值：`0`
* **scrollFind** 当元素不在页面中，是否启用向上滑动继续查找。默认值：`1`
* **scrollCount** 滑动查找的次数。默认值：`5`
* **waitTime** 执行完成后固定等待的时间（ms） 默认值: `1000`
* **toastMessage** 操作完成后检测toast信息

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action LongClick \
-e step-elementTextContains 设置  \
-e step-elementClazz android.widget.TextView  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```

#### ClickByPoint
坐标点击
* **touchX** X坐标
* **touchY** Y坐标
* **toastMessage** 操作完成后检测toast信息

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action ClickByPoint \
-e step-touchX 500  \
-e step-touchY 600  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```
#### OpenApplication
打开某个应用
* **packageName** 需要启动应用的包名
* **clear**  是否清理内存`true`、`false` 默认值:`false`(仅支持安卓5.0以上)
* **toastMessage** 操作完成后检测toast信息

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action OpenApplication \
-e step-packageName com.zhongan.welfaremall  \
-e step-clear true  \
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```

#### AssertExist
页面元素是否存在
* **匹配元素** 所有属性都存在精准、包含、正则三种匹配模式。（例：elementText 、elementTextContains、elementTextPattern ）
    * **elementId** 匹配元素的 resource-id。
    * **elementText** 匹配元素的 text。
    * **elementDesc** 匹配元素的 content-desc。
    * **elementClazz** 匹配元素的 class。
    * **elementPackage** 匹配元素的 package。
    * **elementTextDesc** 匹配元素的 text 或 content-desc。
* **order** 如果有多个匹配元素，匹配第几个元素。默认值：`0`
* **scrollFind** 当元素不在页面中，是否启用向上滑动继续查找。默认值：`1`
* **scrollCount** 滑动查找的次数。默认值：`5`
* **waitTime** 执行完成后固定等待的时间（ms） 默认值:`1000`
* **toastMessage** 操作完成后检测toast信息
* **autoPermit** 处理权限弹窗 默认值: `false`

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action AssertExist \
-e step-elementTextContains 天气  \
-e step-order 1
-e step-scrollFind 2
-e step-waitTime 3000
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```

#### WaitUntilGone
页面元素是否消失
* **匹配元素** 所有属性都存在精准、包含、正则三种匹配模式。（例：elementText 、elementTextContains、elementTextPattern ）
    * **elementId** 匹配元素的 resource-id。
    * **elementText** 匹配元素的 text。
    * **elementDesc** 匹配元素的 content-desc。
    * **elementClazz** 匹配元素的 class。
    * **elementPackage** 匹配元素的 package。
    * **elementTextDesc** 匹配元素的 text 或 content-desc。
* **order** 如果有多个匹配元素，匹配第几个元素。默认值：`0`
* **scrollFind** 当元素不在页面中，是否启用向上滑动继续查找。默认值：`1`
* **scrollCount** 滑动查找的次数。默认值：`5`
* **waitTime** 执行完成后固定等待的时间（ms） 默认值: `1000`
* **toastMessage** 操作完成后检测toast信息
* **autoPermit** 处理权限弹窗 默认值: `false`

```bash
adb shell am instrument -w  -e class 'com.smart.farmer.ExampleInstrumentedTest#step'  \
-e step-action WaitUntilGone \
-e step-elementTextContains 应用程序  \
-e step-scrollFind 2
-e step-waitTime 3000
com.smart.farmer.test/android.support.test.runner.AndroidJUnitRunner
```