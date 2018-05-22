# 权限检查
## 基本使用

用于权限检查，结合rxPermission与检查不同型号手机权限，同时参考glide使用了
透明的fragment来检查权限。

Activity中使用
```java****
new PermissionHelper().with(MainActivity.this).request(new PermissionHelper.OnSuccessListener() {
                    @Override
                    public void onPermissionSuccess() {
                        ...
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);