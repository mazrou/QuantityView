# QuantityView

## Installation

> Add it in your root build.gradle at the end of repositories:

``` gradle
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

> Add the dependency :
``` gradle
	dependencies {
	        implementation 'com.github.mazrou:QuantityView:1.0.1'
	}
```
[![](https://jitpack.io/v/mazrou/QuantityView.svg)](https://jitpack.io/#mazrou/QuantityView)

## Usage 
> To use the Quantity view in your project : 
``` XML
    <com.mazrou.quantityView.QuantityView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
 It will apear like : 
 
 <p align="center">
  <img src="https://user-images.githubusercontent.com/26410564/159140185-790013f4-29d4-4427-9951-76e02cc85278.png" />
</p>

The folowing table represant the other attribute of QuantityView : 

| attribute      | Type    | Default |
|----------------|---------|---------|
| qt_start_from  | integer |    0    |
| qt_max_value   | integer |   100   |
| qt_min_value   | integer |    0    |
| qt_delta_value | integer |    1    |
| qt_enable      | boolean |   true  |
  

### Contribution
Any suggestions are welcome, or you can add a PR (Pull request)


