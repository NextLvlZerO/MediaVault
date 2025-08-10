<template>
  <div class="item-container">
    <div class="item-container-border">
      <div class="item-container-border-body">

      </div>
    </div>
  </div>
</template>



<script>

</script>


<style>
.item-container {
  height: 270px;
  width: 200px;
  perspective: 800px;
}

.item-container-border {
  position: relative;
  height: 100%;
  width: 100%;
  padding: 1px;
  border-radius: 5px;
  background: linear-gradient(-45deg, #9eadba, #4b5359);
  transform-origin: center center -1cm;
  transform: scale(1) rotateY(0deg);
  transform-style: preserve-3d;
  transition: all ease-in-out 0.15s;
  box-shadow: 0 0 80px rgba(255, 255, 255, 0);
}

.item-container-border:hover {
  transform: scale(1) rotateX(6deg) rotateY(6deg);
  box-shadow: 0 0 80px rgba(255, 255, 255, 0.1);
  cursor: pointer;
}

.item-container-border-body {
  height: 100%;
  width: 100%;
  background-color: var(--color-background-lighter);
  border-radius: 4px;
}
</style>
