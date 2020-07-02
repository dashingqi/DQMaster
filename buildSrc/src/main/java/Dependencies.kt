/**
 *
 * @ProjectName: DQMaster
 * @Package:
 * @ClassName: Dependencies
 * @Author: DashingQI
 * @CreateDate: 2020/7/3 12:04 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/3 12:04 AM
 * @UpdateRemark:
 * @Version: 1.0
 */
class Dependencies {

    //统一管理项目中的版本信息
    object Versions{
        // Build Config
        const val minSdkVersion = 19
        const val targetSdkVersion = 29

        const val appcompat = "1.1.0"
    }

    // 统一
    object Deps{
       const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    }
}