### prepare training data
### params example
# note: src=eng, trg=spa are substituted with source and target language name
# start_params=/c15_data/trainingplus/qa/DataCollection/CommonFiles/startparams/v1.1/START.all.parameters
# rc=/c15_data/trainingplus/qa/DataCollection/CommonFiles/rc/eng-spa/default
# customer_name=10530_312876_182525
# train_dir=/data/10530/312876/2014-04-09_11-35-11-566_1/td/select-data/train
# dev_dir=/data/10530/312876/2014-04-09_11-35-11-566_1/td/split/dev_combo
# test_dir=/data/10530/312876/2014-04-09_11-35-11-566_1/td/split/test

### general settings start ###

[general]
src=eng                            # 3-letter code for source language
trg=spa                            # 3-letter code for target language
customer_name=10530_312876_182525        # this appears in the staged LP name in /build/data
#job_name=
patch_level=0
lp_size=8
# use the default value (shown) for these settings:
cdashboard=0                        # never enable cdashboard!
low_disk_mode=1                     # save lots of disk space and still allow debugging
use_sri=0                           # SRI is deprecated -- do not use it anymore

[commands]

parse
create-ats
create-resources
DLP
DLPspeed
BuildDLPspeed

delete-tmp-folders

### common settings

[commonfiles]
dev_start *>> /c15_data/trainingplus/qa/DataCollection/CommonFiles/startparams/v1.1/START.all.parameters                                             # absolute path to START.eng-spa.parameters
rc *>> /c15_data/trainingplus/qa/DataCollection/CommonFiles/rc/eng-spa/default                                                              # absolute path to the rc directory
dev *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/split/dev_combo
test *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/split/test

[commonsettings]
inherit=commonfiles
use_crf_capitalizer=0                   # use the CRF capitalizer instead of the older monolingual one
num_pieces=25
phrase_length=12,12                   # for latin-based languages, 12,12 is typical;  8,8 is also acceptable
run_giza=1                        # tiny/small/medium data should use giza, large/huge set to 0 to use G2 instead
lm_ngram_order=5
lm_map_numbers=1
no_bilex=1
remove_overlap=1
use_zip=1  # since ct4.2

version=5.3

### general settings end ###

### this stanza splits parallel data into dev, test and training sets




#### training a LiSA system ###

[commonsettings-lisa]
inherit=commonsettings
use_crf_capitalizer=0
use_lisa=1
parser_resource_name=PTB-NewsTBv2.0lw_at
other_params= --dt-max-iter=20 --randomize-dt=1 --ptb-beam=6.9 --ptb-gbeam=10 --ptb-lowercase-trees=0 --pbmt-test-num-pieces=10 --decoder-ram-gb=8 --giza-num-threads=8 --giza-threaded=1

[parse]
task=parse
inherit=commonsettings
train *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/select-data/train

[create-ats]
task=create_ats
inherit=commonsettings
train *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/select-data/train

[create-resources]
task=create_lisa_resources
inherit=commonsettings
use_lisa=1
parser_resource_name=PTB-NewsTBv2.0lw_at
ats_prefix=[create-ats]
parse_tree=[parse]
train *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/select-data/train



[DLP]
task=octopus

inherit=commonsettings-lisa
train *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/select-data/train
dev *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/split/dev_combo
test *>> /data/10530/312876/2014-04-09_11-35-11-566_1/td/split/test
lisa_resources *>> [create-resources]/lisa-resources
create_tmem=1
remove_overlap=1


extra_lm0_data=0
CT_add-corpus-info=1



merge_bdb=/c15_data/shasan/compress_lp/eng-spa.compresslp-s4t4.rls//model
lm1 ->> /build2/data/Cinnamon-Release-4G//EngSpaU40_v_5_3_x_1/model/spa.0.lm



### training the language pair is done, time to start QP ###

### speed-test DLP

[DLPspeed]
task=quality_finder
inherit=commonsettings
lp_dir=[DLP]
dt_with_mert=1
machines_8CPUs=1
num_vms=1
q2_speed_desired=2000


[BuildDLPspeed]
task=build
inherit=commonsettings
branch=BuildLang
system=[DLPspeed]
just_stage=1
staging_dir=/c15_data/trainingplus/qa/tpbuild/stage
langid=/c15_data/trainingplus/qa/DataCollection/CommonFiles/langid/eng/default
warmup_test=/c15_data/trainingplus/qa/DataCollection/CommonFiles/test/eng-spa/default/warmup_test.eng
nugget=1



[delete-tmp-folders]
task=exec
wait_for_task=[BuildDLPspeed]
command=rm -rf ../*/tmp
run_without_condor=1