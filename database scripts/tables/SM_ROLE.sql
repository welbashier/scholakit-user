--------------------------------------------------------
--  DDL for Table SM_ROLE
--------------------------------------------------------

  CREATE TABLE "SM_ROLE" 
   (	"ID" NUMBER, 
	"ROLE_DESCRIPTION" VARCHAR2(50 BYTE), 
	"COMMENTS" VARCHAR2(1000 BYTE), 
	"ROLE_CODE" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SM_ROLE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SM_ROLE_PK" ON "SM_ROLE" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger SM_ROLE_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SM_ROLE_TRG" 
BEFORE INSERT ON SM_ROLE 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    NULL;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "SM_ROLE_TRG" ENABLE;
--------------------------------------------------------
--  Constraints for Table SM_ROLE
--------------------------------------------------------

  ALTER TABLE "SM_ROLE" ADD CONSTRAINT "SM_ROLE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "SM_ROLE" MODIFY ("ROLE_DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "SM_ROLE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SM_ROLE" MODIFY ("ROLE_CODE" NOT NULL ENABLE);

REM INSERTING into SM_ROLE
SET DEFINE OFF;
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (1,'Administrator_Level_I','This includes roles such as school administrators, office managers, secretaries, receptionists, and administrative assistants. They handle various administrative tasks, including managing paperwork, coordinating appointments, answering phone calls, and supporting the overall administrative functions of the school.','ROLE_ADMIN');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (2,'Administrator_Level_II','Can be customized to fit any additional admin role','ROLE_ADMIN2');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (3,'Teacher','Teachers have access to features related to classroom management. They can take attendance, record grades, create and manage assignments, communicate with students and parents, and access academic resources.','ROLE_TEACHER');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (4,'Student','Students have limited access to the system and can typically view their attendance records, grades, class schedules, assignments, and other academic-related information. They may also be able to communicate with teachers or submit assignments online.','ROLE_STUDENT');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (5,'Guardian','Parents or guardians have access to their child''s academic information. They can view attendance, grades, class schedules, assignments, communicate with teachers, and receive updates or notifications from the school.','ROLE_GUADIAN');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (6,'Counselor','School counselors provide guidance and support to students regarding personal, social, and academic matters. They help students with goal-setting, career exploration, and emotional well-being. They may also work with parents and teachers to address students'' needs.','ROLE_CNSLR');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (7,'Nurse','The school nurse ensures the health and well-being of students and staff. They provide basic medical care, administer medication, handle medical emergencies, and promote health education and wellness initiatives.','ROLE_NURSE');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (8,'Librarian','If the school has a library system integrated into the management system, a librarian role may be available. Librarians can manage the library''s catalog, check-in/check-out books, track inventory, and handle other library-related tasks.','ROLE_LIBRARIAN');
Insert into SM_ROLE (ID,ROLE_DESCRIPTION,COMMENTS,ROLE_CODE) values (9,'Accountant','These personnel handle financial matters, including budgeting, accounting, payroll processing, fee management, and financial reporting. They ensure accurate financial record-keeping and compliance with financial regulations.','ROLE_ACCOUNTANT');

Commit;