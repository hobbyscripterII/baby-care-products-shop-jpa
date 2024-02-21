-- user
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-10 15:10:21.000000', 1, '2024-01-10 15:10:29.000000', '관리자', 'user@naver.com', NULL, 'ADMIN', '$2a$10$MeX/9t4pubbkh0p5W1LBOuszbqKR/njrXhwa9blRFL4igmZsu31mS', '010-1010-1010', 'LOCAL', 'ADMIN');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-24 12:34:44.000000', 23, '2024-02-01 09:35:06.000000', '허블', 'hubble@gmail.com', NULL, 'hubble', '$2a$10$MeX/9t4pubbkh0p5W1LBOuszbqKR/njrXhwa9blRFL4igmZsu31mS', '010-5571-6794', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 10:15:02.000000', 55, NULL, '이정도', 'leejungdo@gmail.com', NULL, 'jd1386', '$2a$10$9E55wHcJKDXDmO8ktzc0cO1yIzgCDKl33WrSKuzksrvjUWU90ovCK', '010-3192-2910', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 10:17:39.000000', 56, NULL, '김재완', 'jaewan@gmail.com', NULL, 'last2018', '$2a$10$jXJnkzqZ50y4XOtk/u8v6uIBDcjBJzL2w9FwE2bVhE7NBzbFJk0Cy', '010-2215-1234', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 10:19:17.000000', 57, NULL, '김성은', 'sunny@daum.net', NULL, 'sunny3up', '$2a$10$LfUym7fpBtSMEvw0OjO7kuGKw4FJz9L/jpxCFYO.vw2SaRPlvjyIO', '010-4280-1991', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 10:53:36.000000', 58, NULL, '구일모', 'johnny@gmail.com', NULL, 'johnkoo84', '$2a$10$Y.Hk.TP71HjcNxRgPh8KTODhzUGQNzq0q1Y13vVjiYJGYgxxikJfe', '010-8491-3982', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 10:56:53.000000', 59, '2024-02-14 16:31:40.000000', '장원진', 'jang@gmail.com', NULL, 'jangwj2931', '$2a$10$IoAPacGfehCj0n1pfnd7Ne89Eq7mAOwE7EDXkb3kqVNzjCYoMVPx2', '010-4811-0921', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 10:58:12.000000', 60, NULL, '이강인', 'kanginlee@hanmail.net', NULL, 'kanginlee1', '$2a$10$SJxNn.pGK9PSmONBH1M22OGAh5TAzpOqAq.fXCjX9d4QrAVNdhvY.', '010-9311-9411', 'LOCAL', 'USER');
INSERT INTO `t_user` (`unregister_fl`, `created_at`, `iuser`, `updated_at`, `nm`, `email`, `admin_memo`, `uid`, `upw`, `phone_number`, `provider_type`, `role`) VALUES (0, '2024-01-31 11:07:21.000000', 61, '2024-02-06 14:00:14.000000', '정지수', 'jisoocity@naver.com', NULL, 'jisoocity', '$2a$10$K.Z1jeC19u/0F174k4mXcOHADAq76atS85Nj9ZzA5mPKZVuWVMIvq', '010-8591-4011', 'LOCAL', 'USER');
-- address
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (106, 23, '38800', '경북 영천시 신녕면 대학길 5-13', '2', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (107, 23, '41919', '대구 중구 경상감영길 4', '2344', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (111, 23, '42427', '대구 남구 마태산길 5-2', '123', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (64, 55, '13525', '경기도 성남시 분당구', '대왕판교로 160', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (65, 56, '08832', '서울특별시 관악구', '관악로 145', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (66, 57, '16835', '경기도 용인시 수지구', '포은대로 435', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (67, 58, '06090', '서울특별시 강남구', '학동로 426', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (89, 59, '02859', '서울 성북구 안암로 5', '신상 3길', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (116, 59, '38540', '경북 경산시 압량읍 주들길 9', '12', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (69, 60, '55041', '전라북도 전주시 완산구', '풍남동3가 64-1', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (70, 61, '42938', '대구 달성군 가창면 가창동로 3', '공평로 88', '2024-02-21 15:13:41', NULL);
INSERT INTO `t_address` (`iaddress`, `iuser`, `zip_code`, `address`, `address_detail`, `created_at`, `updated_at`) VALUES (71, 61, '42427', '대구 남구 마태산길 5-2', '내방로 1113', '2024-02-21 15:13:41', NULL);
-- childAge
INSERT INTO `t_child_age` (`ichild_age`, `child_age`, `created_at`) VALUES (1, '임신/출산(~0개월)', '2024-01-15 16:52:53');
INSERT INTO `t_child_age` (`ichild_age`, `child_age`, `created_at`) VALUES (2, '신생아(1~3개월)', '2024-01-15 16:53:04');
INSERT INTO `t_child_age` (`ichild_age`, `child_age`, `created_at`) VALUES (3, '베이비(4~23개월)', '2024-01-15 16:53:19');
INSERT INTO `t_child_age` (`ichild_age`, `child_age`, `created_at`) VALUES (4, '키즈(24개월~)', '2024-01-15 16:53:30');
-- userChild
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (7, 23, 'M', 1, '2024-01-24 12:34:44', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (8, 23, 'M', 4, '2024-01-24 12:34:44', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (91, 55, 'M', 4, '2024-01-31 10:15:02', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (92, 56, 'W', 1, '2024-01-31 10:17:39', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (93, 56, 'M', 4, '2024-01-31 10:17:39', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (94, 57, 'W', 1, '2024-01-31 10:19:17', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (95, 57, 'W', 1, '2024-01-31 10:19:17', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (96, 57, 'W', 4, '2024-01-31 10:19:17', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (97, 58, 'M', 2, '2024-01-31 10:53:36', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (98, 58, 'M', 4, '2024-01-31 10:53:36', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (99, 59, 'M', 1, '2024-01-31 10:56:53', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (100, 60, 'W', 1, '2024-01-31 10:58:12', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (101, 61, 'M', 3, '2024-01-31 11:07:21', NULL);
INSERT INTO `t_user_child` (`ichild`, `iuser`, `gender`, `ichild_age`, `created_at`, `updated_at`) VALUES (102, 61, 'W', 1, '2024-01-31 11:07:21', NULL);




