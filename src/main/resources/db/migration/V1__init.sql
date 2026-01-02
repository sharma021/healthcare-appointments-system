-- V1__init.sql
create table users (
  id uuid primary key,
  email varchar(120) not null unique,
  password_hash varchar(200) not null,
  role varchar(20) not null
);

create table patients (
  id uuid primary key,
  user_id uuid not null unique,
  full_name varchar(140) not null,
  phone varchar(30),
  dob_iso varchar(20)
);

create table doctors (
  id uuid primary key,
  user_id uuid not null unique,
  full_name varchar(140) not null,
  specialization varchar(120) not null
);

create table appointments (
  id uuid primary key,
  patient_id uuid not null,
  doctor_id uuid not null,
  start_time timestamp with time zone not null,
  duration_minutes int not null,
  reason varchar(250),
  status varchar(20) not null
);

create index idx_appt_doctor_time on appointments(doctor_id, start_time);
create index idx_appt_patient on appointments(patient_id);

create table ehr_records (
  id uuid primary key,
  appointment_id uuid not null,
  patient_id uuid not null,
  doctor_id uuid not null,
  notes text not null,
  diagnosis varchar(200),
  created_at timestamp with time zone not null
);

create index idx_ehr_patient on ehr_records(patient_id);

create table audit_logs (
  id uuid primary key,
  action varchar(30) not null,
  entity_type varchar(60) not null,
  entity_id uuid not null,
  actor_email varchar(120),
  details text,
  created_at timestamp with time zone not null
);

create index idx_audit_entity on audit_logs(entity_type, entity_id);
