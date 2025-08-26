<template>
  <div>
    <div class="main-section-header">
      <div class="main-container">
        <Information />
      </div>
      <div>
        <SawSvg />
      </div>
    </div>

    <div class="main-section-body" id="body">
      <div class="main-container">
        <MediaContainer fontSize="5" prename="Highest" name="Rated" dataType="movie-rating" :clickable="true" />
        <MediaContainer fontSize="5" prename="W" name="atchlist" dataType="watchlist" :clickable="true" />
        <MediaContainer fontSize="5" id="movies" prename="M" name="ovies" dataType="movies-all" :clickable="true" />
        <MediaContainer id="series" prename="S" name="eries" dataType="series-all" :clickable="true" />
      </div>
    </div>
  </div>
</template>



<script setup>

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
    radial-gradient(ellipse 150px 50% at center, #ffffff22 0%, transparent 100%), linear-gradient(to bottom, var(--color-background-lighter) 20%, var(--color-background) 100%);
  min-height: 500px;
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
