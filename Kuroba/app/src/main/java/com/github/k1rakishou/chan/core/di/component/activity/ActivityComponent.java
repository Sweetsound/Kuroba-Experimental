package com.github.k1rakishou.chan.core.di.component.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.github.k1rakishou.chan.activity.SharingActivity;
import com.github.k1rakishou.chan.activity.StartActivity;
import com.github.k1rakishou.chan.controller.ui.NavigationControllerContainerLayout;
import com.github.k1rakishou.chan.core.di.module.activity.ActivityModule;
import com.github.k1rakishou.chan.core.di.scope.PerActivity;
import com.github.k1rakishou.chan.core.presenter.ImageViewerPresenter;
import com.github.k1rakishou.chan.features.bookmarks.BookmarksController;
import com.github.k1rakishou.chan.features.bookmarks.BookmarksPresenter;
import com.github.k1rakishou.chan.features.bookmarks.BookmarksSortingController;
import com.github.k1rakishou.chan.features.bookmarks.epoxy.BaseThreadBookmarkViewHolder;
import com.github.k1rakishou.chan.features.bookmarks.epoxy.EpoxyGridThreadBookmarkViewHolder;
import com.github.k1rakishou.chan.features.bookmarks.epoxy.EpoxyListThreadBookmarkViewHolder;
import com.github.k1rakishou.chan.features.bypass.SiteAntiSpamCheckBypassController;
import com.github.k1rakishou.chan.features.drawer.MainController;
import com.github.k1rakishou.chan.features.drawer.MainControllerPresenter;
import com.github.k1rakishou.chan.features.drawer.epoxy.EpoxyHistoryGridEntryView;
import com.github.k1rakishou.chan.features.drawer.epoxy.EpoxyHistoryHeaderView;
import com.github.k1rakishou.chan.features.drawer.epoxy.EpoxyHistoryListEntryView;
import com.github.k1rakishou.chan.features.filter_watches.FilterWatchesController;
import com.github.k1rakishou.chan.features.gesture_editor.AdjustAndroid10GestureZonesController;
import com.github.k1rakishou.chan.features.gesture_editor.AdjustAndroid10GestureZonesView;
import com.github.k1rakishou.chan.features.image_saver.ImageSaverV2OptionsController;
import com.github.k1rakishou.chan.features.image_saver.ResolveDuplicateImagesController;
import com.github.k1rakishou.chan.features.image_saver.epoxy.EpoxyDuplicateImageView;
import com.github.k1rakishou.chan.features.login.LoginController;
import com.github.k1rakishou.chan.features.proxies.ProxyEditorController;
import com.github.k1rakishou.chan.features.proxies.ProxySetupController;
import com.github.k1rakishou.chan.features.proxies.epoxy.EpoxyProxyView;
import com.github.k1rakishou.chan.features.reencoding.ImageOptionsController;
import com.github.k1rakishou.chan.features.reencoding.ImageOptionsHelper;
import com.github.k1rakishou.chan.features.reencoding.ImageReencodeOptionsController;
import com.github.k1rakishou.chan.features.reencoding.ImageReencodingPresenter;
import com.github.k1rakishou.chan.features.reordering.EpoxyReorderableItemView;
import com.github.k1rakishou.chan.features.reordering.SimpleListItemsReorderingController;
import com.github.k1rakishou.chan.features.reply.ReplyLayout;
import com.github.k1rakishou.chan.features.reply.ReplyLayoutFilesArea;
import com.github.k1rakishou.chan.features.reply.epoxy.EpoxyAttachNewFileButtonView;
import com.github.k1rakishou.chan.features.reply.epoxy.EpoxyAttachNewFileButtonWideView;
import com.github.k1rakishou.chan.features.reply.epoxy.EpoxyReplyFileView;
import com.github.k1rakishou.chan.features.search.GlobalSearchController;
import com.github.k1rakishou.chan.features.search.SearchResultsController;
import com.github.k1rakishou.chan.features.search.SelectBoardForSearchController;
import com.github.k1rakishou.chan.features.search.SelectSiteForSearchController;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxyBoardSelectionButtonView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySearchEndOfResultsView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySearchErrorView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySearchPostDividerView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySearchPostGapView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySearchPostView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySearchSiteView;
import com.github.k1rakishou.chan.features.search.epoxy.EpoxySelectableBoardItemView;
import com.github.k1rakishou.chan.features.settings.MainSettingsControllerV2;
import com.github.k1rakishou.chan.features.settings.SettingsCoordinator;
import com.github.k1rakishou.chan.features.settings.epoxy.EpoxyBooleanSetting;
import com.github.k1rakishou.chan.features.settings.epoxy.EpoxyLinkSetting;
import com.github.k1rakishou.chan.features.settings.epoxy.EpoxyNoSettingsFoundView;
import com.github.k1rakishou.chan.features.settings.epoxy.EpoxySettingsGroupTitle;
import com.github.k1rakishou.chan.features.setup.AddBoardsController;
import com.github.k1rakishou.chan.features.setup.BoardSelectionController;
import com.github.k1rakishou.chan.features.setup.BoardsSetupController;
import com.github.k1rakishou.chan.features.setup.SiteSettingsController;
import com.github.k1rakishou.chan.features.setup.SitesSetupController;
import com.github.k1rakishou.chan.features.setup.epoxy.EpoxyBoardView;
import com.github.k1rakishou.chan.features.setup.epoxy.EpoxySelectableBoardView;
import com.github.k1rakishou.chan.features.setup.epoxy.selection.EpoxyBoardSelectionGridView;
import com.github.k1rakishou.chan.features.setup.epoxy.selection.EpoxyBoardSelectionListView;
import com.github.k1rakishou.chan.features.setup.epoxy.selection.EpoxySiteSelectionView;
import com.github.k1rakishou.chan.features.setup.epoxy.site.EpoxySiteView;
import com.github.k1rakishou.chan.features.themes.ThemeGalleryController;
import com.github.k1rakishou.chan.features.themes.ThemeSettingsController;
import com.github.k1rakishou.chan.ui.adapter.PostAdapter;
import com.github.k1rakishou.chan.ui.captcha.CaptchaLayout;
import com.github.k1rakishou.chan.ui.captcha.GenericWebViewAuthenticationLayout;
import com.github.k1rakishou.chan.ui.captcha.v1.CaptchaNojsLayoutV1;
import com.github.k1rakishou.chan.ui.captcha.v2.CaptchaNoJsLayoutV2;
import com.github.k1rakishou.chan.ui.cell.AlbumViewCell;
import com.github.k1rakishou.chan.ui.cell.CardPostCell;
import com.github.k1rakishou.chan.ui.cell.PostCell;
import com.github.k1rakishou.chan.ui.cell.PostStubCell;
import com.github.k1rakishou.chan.ui.cell.ThreadStatusCell;
import com.github.k1rakishou.chan.ui.cell.post_thumbnail.PostImageThumbnailView;
import com.github.k1rakishou.chan.ui.cell.post_thumbnail.PostImageThumbnailViewContainer;
import com.github.k1rakishou.chan.ui.controller.AlbumDownloadController;
import com.github.k1rakishou.chan.ui.controller.AlbumViewController;
import com.github.k1rakishou.chan.ui.controller.BrowseController;
import com.github.k1rakishou.chan.ui.controller.CaptchaContainerController;
import com.github.k1rakishou.chan.ui.controller.FiltersController;
import com.github.k1rakishou.chan.ui.controller.FloatingListMenuController;
import com.github.k1rakishou.chan.ui.controller.ImageViewerController;
import com.github.k1rakishou.chan.ui.controller.ImageViewerGesturesSettingsController;
import com.github.k1rakishou.chan.ui.controller.ImageViewerNavigationController;
import com.github.k1rakishou.chan.ui.controller.LicensesController;
import com.github.k1rakishou.chan.ui.controller.LoadingViewController;
import com.github.k1rakishou.chan.ui.controller.LogsController;
import com.github.k1rakishou.chan.ui.controller.PopupController;
import com.github.k1rakishou.chan.ui.controller.PostLinksController;
import com.github.k1rakishou.chan.ui.controller.RemovedPostsController;
import com.github.k1rakishou.chan.ui.controller.ReportController;
import com.github.k1rakishou.chan.ui.controller.ReportProblemController;
import com.github.k1rakishou.chan.ui.controller.ThreadSlideController;
import com.github.k1rakishou.chan.ui.controller.ViewThreadController;
import com.github.k1rakishou.chan.ui.controller.crashlogs.ReviewReportFilesController;
import com.github.k1rakishou.chan.ui.controller.crashlogs.ViewFullCrashLogController;
import com.github.k1rakishou.chan.ui.controller.dialog.KurobaAlertDialogHostController;
import com.github.k1rakishou.chan.ui.controller.navigation.BottomNavBarAwareNavigationController;
import com.github.k1rakishou.chan.ui.controller.navigation.SplitNavigationController;
import com.github.k1rakishou.chan.ui.controller.navigation.StyledToolbarNavigationController;
import com.github.k1rakishou.chan.ui.controller.navigation.TabHostController;
import com.github.k1rakishou.chan.ui.controller.popup.PostRepliesPopupController;
import com.github.k1rakishou.chan.ui.controller.popup.PostSearchPopupController;
import com.github.k1rakishou.chan.ui.controller.settings.RangeSettingUpdaterController;
import com.github.k1rakishou.chan.ui.controller.settings.captcha.JsCaptchaCookiesEditorController;
import com.github.k1rakishou.chan.ui.controller.settings.captcha.JsCaptchaCookiesEditorLayout;
import com.github.k1rakishou.chan.ui.epoxy.EpoxyDividerView;
import com.github.k1rakishou.chan.ui.epoxy.EpoxyErrorView;
import com.github.k1rakishou.chan.ui.epoxy.EpoxyExpandableGroupView;
import com.github.k1rakishou.chan.ui.epoxy.EpoxyPostLink;
import com.github.k1rakishou.chan.ui.epoxy.EpoxySimpleGroupView;
import com.github.k1rakishou.chan.ui.epoxy.EpoxyTextView;
import com.github.k1rakishou.chan.ui.epoxy.EpoxyTextViewWrapHeight;
import com.github.k1rakishou.chan.ui.helper.RemovedPostsHelper;
import com.github.k1rakishou.chan.ui.layout.FilterLayout;
import com.github.k1rakishou.chan.ui.layout.PopupControllerContainer;
import com.github.k1rakishou.chan.ui.layout.PostPopupContainer;
import com.github.k1rakishou.chan.ui.layout.ReportProblemLayout;
import com.github.k1rakishou.chan.ui.layout.SearchLayout;
import com.github.k1rakishou.chan.ui.layout.SplitNavigationControllerLayout;
import com.github.k1rakishou.chan.ui.layout.ThreadLayout;
import com.github.k1rakishou.chan.ui.layout.ThreadListLayout;
import com.github.k1rakishou.chan.ui.layout.ThreadSlidingPaneLayout;
import com.github.k1rakishou.chan.ui.layout.crashlogs.ReviewReportFilesLayout;
import com.github.k1rakishou.chan.ui.layout.crashlogs.ViewFullReportFileLayout;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableBarButton;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableButton;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableCardView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableCheckBox;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableChip;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableDivider;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableEditText;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableEpoxyRecyclerView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableFloatingActionButton;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableGridRecyclerView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableListView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableProgressBar;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableRadioButton;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableRecyclerView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableScrollView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableSlider;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableSwitchMaterial;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableTabLayout;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableTextInputLayout;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableTextView;
import com.github.k1rakishou.chan.ui.theme.widget.ColorizableToolbarSearchLayoutEditText;
import com.github.k1rakishou.chan.ui.theme.widget.TouchBlockingConstraintLayout;
import com.github.k1rakishou.chan.ui.theme.widget.TouchBlockingCoordinatorLayout;
import com.github.k1rakishou.chan.ui.theme.widget.TouchBlockingFrameLayout;
import com.github.k1rakishou.chan.ui.theme.widget.TouchBlockingLinearLayout;
import com.github.k1rakishou.chan.ui.toolbar.Toolbar;
import com.github.k1rakishou.chan.ui.toolbar.ToolbarContainer;
import com.github.k1rakishou.chan.ui.toolbar.ToolbarMenuItem;
import com.github.k1rakishou.chan.ui.view.FastScroller;
import com.github.k1rakishou.chan.ui.view.FloatingMenu;
import com.github.k1rakishou.chan.ui.view.HidingFloatingActionButton;
import com.github.k1rakishou.chan.ui.view.LoadingBar;
import com.github.k1rakishou.chan.ui.view.MultiImageView;
import com.github.k1rakishou.chan.ui.view.OptionalSwipeViewPager;
import com.github.k1rakishou.chan.ui.view.ReplyInputEditText;
import com.github.k1rakishou.chan.ui.view.ThumbnailView;
import com.github.k1rakishou.chan.ui.view.ViewContainerWithMaxSize;
import com.github.k1rakishou.chan.ui.view.attach.AttachNewFileButton;
import com.github.k1rakishou.chan.ui.view.bottom_menu_panel.BottomMenuPanel;
import com.github.k1rakishou.chan.ui.view.floating_menu.epoxy.EpoxyCheckableFloatingListMenuRow;
import com.github.k1rakishou.chan.ui.view.floating_menu.epoxy.EpoxyFloatingListMenuRow;
import com.github.k1rakishou.chan.ui.view.floating_menu.epoxy.EpoxyHeaderListMenuRow;
import com.github.k1rakishou.chan.ui.view.sorting.BookmarkSortingItemView;
import com.github.k1rakishou.chan.ui.widget.dialog.KurobaAlertController;

import dagger.BindsInstance;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(StartActivity startActivity);
    void inject(SharingActivity sharingActivity);

    void inject(AlbumDownloadController albumDownloadController);
    void inject(AlbumViewController albumViewController);
    void inject(BrowseController browseController);
    void inject(MainController mainController);
    void inject(FiltersController filtersController);
    void inject(ImageOptionsController imageOptionsController);
    void inject(ImageReencodeOptionsController imageReencodeOptionsController);
    void inject(ImageViewerController imageViewerController);
    void inject(ImageViewerNavigationController imageViewerNavigationController);
    void inject(LicensesController licensesController);
    void inject(LoginController loginController);
    void inject(LogsController logsController);
    void inject(PopupController popupController);
    void inject(PostRepliesPopupController postRepliesPopupController);
    void inject(PostSearchPopupController postSearchPopupController);
    void inject(RemovedPostsController removedPostsController);
    void inject(ReportController reportController);
    void inject(SitesSetupController sitesSetupController);
    void inject(SplitNavigationController splitNavigationController);
    void inject(StyledToolbarNavigationController styledToolbarNavigationController);
    void inject(ThemeSettingsController themeSettingsController);
    void inject(ThemeGalleryController themeGalleryController);
    void inject(ThreadSlideController threadSlideController);
    void inject(ViewThreadController viewThreadController);
    void inject(AdjustAndroid10GestureZonesController adjustAndroid10GestureZonesController);
    void inject(BookmarksController bookmarksController);
    void inject(RangeSettingUpdaterController rangeSettingUpdaterController);
    void inject(BookmarksSortingController bookmarksSortingController);
    void inject(ProxyEditorController proxyEditorController);
    void inject(ProxySetupController proxySetupController);
    void inject(GlobalSearchController globalSearchController);
    void inject(SearchResultsController searchResultsController);
    void inject(AddBoardsController addBoardsController);
    void inject(BoardsSetupController boardsSetupController);
    void inject(MainSettingsControllerV2 mainSettingsControllerV2);
    void inject(SiteSettingsController siteSettingsController);
    void inject(ImageViewerGesturesSettingsController imageViewerGesturesSettingsController);
    void inject(ReportProblemController reportProblemController);
    void inject(ReviewReportFilesController reviewReportFilesController);
    void inject(ViewFullCrashLogController viewFullCrashLogController);
    void inject(FloatingListMenuController floatingListMenuController);
    void inject(BottomNavBarAwareNavigationController bottomNavBarAwareNavigationController);
    void inject(JsCaptchaCookiesEditorController jsCaptchaCookiesEditorController);
    void inject(LoadingViewController loadingViewController);
    void inject(BoardSelectionController boardSelectionController);
    void inject(PostLinksController postLinksController);
    void inject(SelectSiteForSearchController selectSiteForSearchController);
    void inject(SelectBoardForSearchController selectBoardForSearchController);
    void inject(SiteAntiSpamCheckBypassController siteAntiSpamCheckBypassController);
    void inject(TabHostController tabHostController);
    void inject(FilterWatchesController filterWatchesController);
    void inject(ImageSaverV2OptionsController imageSaverV2OptionsController);
    void inject(ResolveDuplicateImagesController resolveDuplicateImagesController);
    void inject(KurobaAlertDialogHostController kurobaAlertDialogHostController);
    void inject(SimpleListItemsReorderingController simpleListItemsReorderingController);
    void inject(CaptchaContainerController captchaContainerController);

    void inject(ColorizableBarButton colorizableBarButton);
    void inject(ColorizableButton colorizableButton);
    void inject(ColorizableCardView colorizableCardView);
    void inject(ColorizableCheckBox colorizableCheckBox);
    void inject(ColorizableChip colorizableChip);
    void inject(ColorizableEditText colorizableEditText);
    void inject(ColorizableDivider colorizableDivider);
    void inject(ColorizableEpoxyRecyclerView colorizableEpoxyRecyclerView);
    void inject(ColorizableFloatingActionButton colorizableFloatingActionButton);
    void inject(ColorizableListView colorizableListView);
    void inject(ColorizableProgressBar colorizableProgressBar);
    void inject(ColorizableRadioButton colorizableRadioButton);
    void inject(ColorizableRecyclerView colorizableRecyclerView);
    void inject(ColorizableGridRecyclerView colorizableGridRecyclerView);
    void inject(ColorizableScrollView colorizableScrollView);
    void inject(ColorizableSlider colorizableSlider);
    void inject(ColorizableSwitchMaterial colorizableSwitchMaterial);
    void inject(ColorizableTextInputLayout colorizableTextInputLayout);
    void inject(ColorizableTextView colorizableTextView);
    void inject(ReplyInputEditText replyInputEditText);
    void inject(ColorizableTabLayout colorizableTabLayout);
    void inject(ColorizableToolbarSearchLayoutEditText colorizableToolbarSearchLayoutEditText);

    void inject(EpoxyGridThreadBookmarkViewHolder epoxyGridThreadBookmarkViewHolder);
    void inject(EpoxyListThreadBookmarkViewHolder epoxyListThreadBookmarkViewHolder);
    void inject(EpoxyHistoryListEntryView epoxyHistoryListEntryView);
    void inject(EpoxyHistoryGridEntryView epoxyHistoryGridEntryView);
    void inject(EpoxyProxyView epoxyProxyView);
    void inject(EpoxySearchEndOfResultsView epoxySearchEndOfResultsView);
    void inject(EpoxySearchErrorView epoxySearchErrorView);
    void inject(EpoxySearchPostDividerView epoxySearchPostDividerView);
    void inject(EpoxySearchPostGapView epoxySearchPostGapView);
    void inject(EpoxySearchPostView epoxySearchPostView);
    void inject(EpoxySearchSiteView epoxySearchSiteView);
    void inject(EpoxyBooleanSetting epoxyBooleanSetting);
    void inject(EpoxyLinkSetting epoxyLinkSetting);
    void inject(EpoxyNoSettingsFoundView epoxyNoSettingsFoundView);
    void inject(EpoxySettingsGroupTitle epoxySettingsGroupTitle);
    void inject(EpoxyBoardView epoxyBoardView);
    void inject(EpoxySelectableBoardView epoxySelectableBoardView);
    void inject(EpoxyBoardSelectionListView epoxyBoardSelectionListView);
    void inject(EpoxyBoardSelectionGridView epoxyBoardSelectionGridView);
    void inject(EpoxySiteSelectionView epoxySiteSelectionView);
    void inject(EpoxySiteView epoxySiteView);
    void inject(EpoxyDividerView epoxyDividerView);
    void inject(EpoxyErrorView epoxyErrorView);
    void inject(EpoxyExpandableGroupView epoxyExpandableGroupView);
    void inject(EpoxyTextView epoxyTextView);
    void inject(EpoxyCheckableFloatingListMenuRow epoxyCheckableFloatingListMenuRow);
    void inject(EpoxyFloatingListMenuRow epoxyFloatingListMenuRow);
    void inject(EpoxyHeaderListMenuRow epoxyHeaderListMenuRow);
    void inject(EpoxyHistoryHeaderView epoxyHistoryHeaderView);
    void inject(EpoxyReplyFileView epoxyReplyFileView);
    void inject(EpoxyAttachNewFileButtonView epoxyAttachNewFileButtonView);
    void inject(EpoxyAttachNewFileButtonWideView epoxyAttachNewFileButtonWideView);
    void inject(EpoxyTextViewWrapHeight epoxyTextViewWrapHeight);
    void inject(EpoxyPostLink epoxyPostLink);
    void inject(EpoxyBoardSelectionButtonView epoxyBoardSelectionButtonView);
    void inject(EpoxySelectableBoardItemView epoxySelectableBoardItemView);
    void inject(EpoxySimpleGroupView epoxySimpleGroupView);
    void inject(EpoxyDuplicateImageView epoxyDuplicateImageView);
    void inject(EpoxyReorderableItemView epoxyReorderableItemView);

    void inject(CaptchaNoJsLayoutV2 captchaNoJsLayoutV2);
    void inject(CaptchaNojsLayoutV1 captchaNojsLayoutV1);
    void inject(ThumbnailView thumbnailView);
    void inject(PostImageThumbnailView thumbnailView);
    void inject(MultiImageView multiImageView);
    void inject(ThreadLayout threadLayout);
    void inject(FilterLayout filterLayout);
    void inject(ReplyLayout replyLayout);
    void inject(ThreadListLayout threadListLayout);
    void inject(ToolbarContainer toolbarContainer);
    void inject(CardPostCell cardPostCell);
    void inject(CaptchaLayout captchaLayout);
    void inject(FloatingMenu floatingMenu);
    void inject(Toolbar toolbar);
    void inject(ThreadStatusCell threadStatusCell);
    void inject(PostCell postCell);
    void inject(AlbumViewCell albumViewCell);
    void inject(NavigationControllerContainerLayout navigationControllerContainerLayout);
    void inject(BookmarksPresenter bookmarksPresenter);
    void inject(BaseThreadBookmarkViewHolder baseThreadBookmarkViewHolder);
    void inject(MainControllerPresenter mainControllerPresenter);
    void inject(AdjustAndroid10GestureZonesView adjustAndroid10GestureZonesView);
    void inject(SettingsCoordinator settingsCoordinator);
    void inject(JsCaptchaCookiesEditorLayout jsCaptchaCookiesEditorLayout);
    void inject(ReportProblemLayout reportProblemLayout);
    void inject(ReviewReportFilesLayout reviewReportFilesLayout);
    void inject(ViewFullReportFileLayout viewFullReportFileLayout);
    void inject(HidingFloatingActionButton hidingFloatingActionButton);
    void inject(TouchBlockingConstraintLayout touchBlockingConstraintLayout);
    void inject(TouchBlockingCoordinatorLayout touchBlockingCoordinatorLayout);
    void inject(TouchBlockingFrameLayout touchBlockingFrameLayout);
    void inject(TouchBlockingLinearLayout touchBlockingLinearLayout);
    void inject(ViewContainerWithMaxSize viewContainerWithMaxSize);
    void inject(BottomMenuPanel bottomMenuPanel);
    void inject(BookmarkSortingItemView bookmarkSortingItemView);
    void inject(GenericWebViewAuthenticationLayout genericWebViewAuthenticationLayout);
    void inject(PostAdapter postAdapter);
    void inject(RemovedPostsHelper removedPostsHelper);
    void inject(ImageOptionsHelper imageOptionsHelper);
    void inject(ImageReencodingPresenter imageReencodingPresenter);
    void inject(ImageViewerPresenter imageViewerPresenter);
    void inject(LoadingBar loadingBar);
    void inject(ThreadSlidingPaneLayout threadSlidingPaneLayout);
    void inject(PostStubCell postStubCell);
    void inject(PostPopupContainer postPopupContainer);
    void inject(SearchLayout searchLayout);
    void inject(SplitNavigationControllerLayout splitNavigationControllerLayout);
    void inject(ReplyLayoutFilesArea replyLayoutFilesArea);
    void inject(AttachNewFileButton attachNewFileButton);
    void inject(OptionalSwipeViewPager optionalSwipeViewPager);
    void inject(FastScroller fastScroller);
    void inject(PopupControllerContainer popupControllerContainer);
    void inject(ToolbarMenuItem toolbarMenuItem);
    void inject(KurobaAlertController kurobaAlertController);
    void inject(PostImageThumbnailViewContainer postImageThumbnailViewContainer);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder activity(AppCompatActivity activity);
        @BindsInstance
        Builder activityModule(ActivityModule module);

        ActivityComponent build();
    }
}
