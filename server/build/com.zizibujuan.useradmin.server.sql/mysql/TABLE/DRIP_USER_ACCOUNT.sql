-- -----------------------------------------------------
-- Table `DRIP_USER_ACCOUNT` 用户在各网站或聊天工具中申请的帐号
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DRIP_USER_ACCOUNT`;

CREATE  TABLE IF NOT EXISTS `DRIP_USER_ACCOUNT` (
  `DBID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键' ,
  `GLOBAL_USER_ID` BIGINT UNSIGNED NOT NULL COMMENT '全局用户标识，但是只存储为本网站注册用户生成的用户标识' ,
  `SOFTWARE_CODE` VARCHAR(16) NULL COMMENT '软件代码，用软件的缩写名标识',
  `ACCOUNT` VARCHAR(128) NULL COMMENT '帐号',
  `CREATE_TIME` DATETIME NOT NULL COMMENT '创建时间' ,
  `UPDATE_TIME` DATETIME NULL COMMENT '更新时间',
  PRIMARY KEY (`DBID`))
ENGINE = InnoDB
COMMENT = '用户帐号';