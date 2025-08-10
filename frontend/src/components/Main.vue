<template>
  <div>
    <div class="main-section-header">
      <div class="main-container">
        <Information />
      </div>
      <div class="wave">
        <svg width="100%" height="100%" id="svg" viewBox="0 0 1440 390" xmlns="http://www.w3.org/2000/svg"
          class="transition duration-300 ease-in-out delay-150">
          <defs>
            <linearGradient id="gradient" x1="100%" y1="100%" x2="100%" y2="0%">
              <stop offset="5%" stop-color=var(--color-background-lighter)></stop>
              <stop offset="95%" stop-color=var(--color-background-lighter)></stop>
            </linearGradient>
          </defs>
          <path
            d="M 0,400 L 0,0 C 42.63813699254115,34.99406612385887 85.2762739850823,69.98813224771774 125,76 C 164.7237260149177,82.01186775228226 201.533041052212,59.04153713298791 241,70 C 280.466958947788,80.95846286701209 322.5915618060696,125.84571922033061 363,136 C 403.4084381939304,146.1542807796694 442.10071172350956,121.57558598568963 477,113 C 511.89928827649044,104.42441401431037 543.0055912998919,111.85193683691091 581,124 C 618.9944087001081,136.1480631630891 663.876923076923,153.01666666666668 707,139 C 750.123076923077,124.98333333333332 791.486716392416,80.0813964964224 831,79 C 870.513283607584,77.9186035035776 908.1762113534132,120.65774734764372 946,134 C 983.8237886465868,147.34225265235628 1021.8084381939304,131.2876141130027 1064,112 C 1106.1915618060696,92.7123858869973 1152.5900358708648,70.19179620034542 1196,85 C 1239.4099641291352,99.80820379965458 1279.83141832261,151.9452010856156 1320,144 C 1360.16858167739,136.0547989143844 1400.0842908386949,68.0273994571922 1440,0 L 1440,400 L 0,400 Z"
            stroke="none" stroke-width="0" fill="url(#gradient)" fill-opacity="0.53"
            class="transition-all duration-300 ease-in-out delay-150 path-0"></path>
          <defs>
            <linearGradient id="gradient" x1="100%" y1="100%" x2="100%" y2="0%">
              <stop offset="5%" stop-color="#fff"></stop>
              <stop offset="95%" stop-color="#000000"></stop>
            </linearGradient>
          </defs>
          <path
            d="M 0,400 L 0,0 C 33.086318776215165,78.80754142230828 66.17263755243033,157.61508284461655 108,189 C 149.82736244756967,220.38491715538345 200.39576856649393,204.34721004384215 241,216 C 281.6042314335061,227.65278995615785 312.24428818159396,266.99607698001483 354,269 C 395.75571181840604,271.00392301998517 448.6270787071304,235.6684820360986 488,231 C 527.3729212928696,226.3315179639014 553.2473969898841,252.32999487559073 595,247 C 636.7526030101159,241.67000512440927 694.3833333333333,205.01153846153846 739,191 C 783.6166666666667,176.98846153846154 815.2192696767827,185.62385127825542 846,188 C 876.7807303232173,190.37614872174458 906.7395879595362,186.49305642543985 946,197 C 985.2604120404638,207.50694357456015 1033.822378485073,232.40392301998517 1074,223 C 1114.177621514927,213.59607698001483 1145.9708981001727,169.89125149461938 1181,185 C 1216.0291018998273,200.10874850538062 1254.2940291142365,274.0310710015373 1298,253 C 1341.7059708857635,231.96892899846267 1390.8529854428816,115.98446449923134 1440,0 L 1440,400 L 0,400 Z"
            stroke="none" stroke-width="0" fill="url(#gradient)" fill-opacity="1"
            class="transition-all duration-300 ease-in-out delay-150 path-1"></path>
        </svg>
      </div>
    </div>

    <div class="main-section-body" id="body">

      <div class="main-container">
        <div class="main-container-title-space">
          <div class="main-container-title">
            <p class="main-container-title-media">{{ "Highest \b" }}</p>
            <p class="main-container-title-vault"> Rated</p>
          </div>
        </div>

        <MediaList />

        <div class="main-container-title-space">
          <div class="main-container-title">
            <p class="main-container-title-media">M</p>
            <p class="main-container-title-vault">ovies</p>
          </div>
        </div>

        <MediaList />

        <div class="main-container-title-space">
          <div class="main-container-title">
            <p class="main-container-title-media">S</p>
            <p class="main-container-title-vault">eries</p>
          </div>
        </div>

        <MediaList />

      </div>
    </div>
  </div>
</template>



<script>
import { RouterLink, RouterView } from 'vue-router';
import MediaList from './Media/MediaList.vue';
import Information from './Information.vue';

export default {
  components: {
    MediaList,
    Information,
  }
}
</script>




<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
  padding: 0rem 2rem;
  font-weight: normal;
}

.main-section-header {}


.main-section-body {

  background:
    radial-gradient(ellipse 150px 50% at center, #ffffff22 0%, transparent 100%), linear-gradient(to bottom, var(--color-background-lighter), var(--color-background));
}

.wave {
  fill: #12303b;
}

.main-container-title {
  position: relative;
  display: flex;
  justify-content: center;
  font-family: var(--font-family-main);
  font-size: 5rem;
  margin-top: 5rem;
}

.main-container-title::after {
  position: absolute;
  content: '';
  width: 30%;
  height: 0px;
  background: linear-gradient(to right, #ffffff00, #ffffff77, #ffffff00);
  bottom: 25%;
  left: 50%;
  transform: translateX(-50%);
}

.main-container-title-space {
  padding: 0rem 0rem;
}

.main-container-title-media {
  font-weight: 600;
  color: var(--color-primary);
}

.main-container-title-vault {
  font-weight: 100;
  background: linear-gradient(to top, #87aad4, white);
  background-clip: text;
  color: transparent;
}
</style>
